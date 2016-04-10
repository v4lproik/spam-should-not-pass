package net.v4lproik.spamshouldnotpass.platform.controllers;

import com.google.common.collect.Lists;
import net.v4lproik.spamshouldnotpass.platform.models.BasicMember;
import net.v4lproik.spamshouldnotpass.platform.models.PlatformException;
import net.v4lproik.spamshouldnotpass.platform.models.dto.*;
import net.v4lproik.spamshouldnotpass.platform.models.entities.CompositePKRulesInContext;
import net.v4lproik.spamshouldnotpass.platform.models.entities.Context;
import net.v4lproik.spamshouldnotpass.platform.models.entities.Rule;
import net.v4lproik.spamshouldnotpass.platform.models.entities.RuleInContext;
import net.v4lproik.spamshouldnotpass.platform.models.response.ContextsResponse;
import net.v4lproik.spamshouldnotpass.platform.models.response.PlatformResponse;
import net.v4lproik.spamshouldnotpass.platform.repositories.CacheSessionRepository;
import net.v4lproik.spamshouldnotpass.platform.repositories.ContextRepository;
import net.v4lproik.spamshouldnotpass.spring.annotation.UserAccess;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping(value = "/context")
public class ContextController {

    @Autowired
    private ContextRepository contextRepository;

    @UserAccess
    @RequestMapping(value = "/get", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public PlatformResponse get(@RequestBody toGetContextDTO toGet) throws PlatformException {

        final Context context = contextRepository.findById(toGet.getId()).orElseThrow(() -> new PlatformException(PlatformResponse.Error.NOT_FOUND, "The context cannot be found"));

        return new ContextsResponse(
                Lists.newArrayList(
                        convertToDTO(context, false)
                )
        );
    }

    @UserAccess
    @RequestMapping(value = "/get-and-rules", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public PlatformResponse getAndRules(@RequestBody toGetContextDTO toGet) throws PlatformException {

        final Context context = contextRepository.findByIdWithRules(toGet.getId()).orElseThrow(() -> new PlatformException(PlatformResponse.Error.NOT_FOUND, "The context cannot be found"));

        return new ContextsResponse(
                Lists.newArrayList(
                        convertToDTO(context, true)
                )
        );
    }

    @UserAccess
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public PlatformResponse list(HttpServletRequest req) {

        final UUID userId = ((BasicMember) req.getAttribute(CacheSessionRepository.MEMBER_KEY)).getId();

        List<Context> contexts = contextRepository.listByUserId(userId);

        List<ContextDTO> contextsDTO = Lists.newArrayList();
        for (Context context: contexts){
            contextsDTO.add(
                    convertToDTO(context, false)
            );
        }

        return new ContextsResponse(contextsDTO);
    }


    @UserAccess
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public PlatformResponse create(HttpServletRequest req,
                                   @RequestBody toCreateContextDTO toCreate) {

        final UUID userId = ((BasicMember) req.getAttribute(CacheSessionRepository.MEMBER_KEY)).getId();

        if (contextRepository.findByName(toCreate.getName(), userId).isPresent()){
            return new ContextsResponse(PlatformResponse.Status.NOK,
                    PlatformResponse.Error.INVALID_INPUT,
                    String.format("Context's name [%s] has already been created", toCreate.getName()));
        }

        UUID contextId = toCreate.getId();
        if (toCreate.getId() == null){
            contextId = UUID.randomUUID();
        }

        final Context context = new Context(
                contextId,
                toCreate.getName(),
                userId,
                DateTime.now(),
                DateTime.now()
        );

        contextRepository.save(context);

        return PlatformResponse.ok();
    }

    @UserAccess
    @RequestMapping(value = "/add-rules", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public PlatformResponse addRules(HttpServletRequest req,
                                     @RequestBody RulesInContextDTO toCreate) throws PlatformException {

        final Context context = contextRepository.findById(toCreate.getIdContext()).orElseThrow(() -> new PlatformException(PlatformResponse.Error.NOT_FOUND, "The context cannot be found"));

        final UUID userId = context.getUserId();

        if (!userId.equals(((BasicMember) req.getAttribute(CacheSessionRepository.MEMBER_KEY)).getId())){
            return new ContextsResponse(PlatformResponse.Status.NOK, PlatformResponse.Error.INVALID_PERMISSION, "Permission is not enough to update this context");
        }

        for (toGetRuleDTO ruleId:toCreate.getListRules()){
            contextRepository.addRule(
                    new RuleInContext(
                            new CompositePKRulesInContext(ruleId.getId(), userId)
                    )
            );
        }

        return PlatformResponse.ok();
    }

    @UserAccess
    @RequestMapping(value = "/update-and-rules", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public PlatformResponse updateAndRules(HttpServletRequest req,
                                           @RequestBody toUpdateContextDTO toUpdate) throws PlatformException {

        final Context context = contextRepository.findById(toUpdate.getId()).orElseThrow(() -> new PlatformException(PlatformResponse.Error.NOT_FOUND, "The context cannot be found"));
        final UUID userId = context.getUserId();
        final DateTime date = context.getDate();

        if (!userId.equals(((BasicMember) req.getAttribute(CacheSessionRepository.MEMBER_KEY)).getId())){
            return new ContextsResponse(PlatformResponse.Status.NOK, PlatformResponse.Error.INVALID_PERMISSION, "Permission is not enough to update this context");
        }

        contextRepository.update(
                new Context(
                        toUpdate.getId(),
                        toUpdate.getName(),
                        userId,
                        date,
                        DateTime.now()
                ));

        if (toUpdate.getRulesId() != null){
            for (UUID ruleId:toUpdate.getRulesId()){
                contextRepository.addRule(
                        new RuleInContext(
                                new CompositePKRulesInContext(ruleId, toUpdate.getId())
                        )
                );
            }
        }

        return PlatformResponse.ok();
    }

    @UserAccess
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public PlatformResponse update(HttpServletRequest req,
                                   @RequestBody toCreateRuleDTO toUpdate) throws PlatformException {

        final Context context = contextRepository.findById(toUpdate.getId()).orElseThrow(() -> new PlatformException(PlatformResponse.Error.NOT_FOUND, "The context cannot be found"));
        final UUID userId = context.getUserId();
        final DateTime date = context.getDate();

        if (!userId.equals(((BasicMember) req.getAttribute(CacheSessionRepository.MEMBER_KEY)).getId())){
            return new ContextsResponse(PlatformResponse.Status.NOK, PlatformResponse.Error.INVALID_PERMISSION, "Permission is not enough to update this context");
        }

        contextRepository.update(
                new Context(
                        toUpdate.getId(),
                        toUpdate.getName(),
                        userId,
                        date,
                        DateTime.now()
                ));

        return ContextsResponse.ok();
    }

    @UserAccess
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public PlatformResponse delete(HttpServletRequest req,
                                   @RequestBody toGetContextDTO toGet) throws PlatformException {

        final Context context = contextRepository.findById(toGet.getId()).orElseThrow(() -> new PlatformException(PlatformResponse.Error.NOT_FOUND, "The context cannot be found"));
        final UUID userId = context.getUserId();

        if (!userId.equals(((BasicMember) req.getAttribute(CacheSessionRepository.MEMBER_KEY)).getId())){
            return new ContextsResponse(PlatformResponse.Status.NOK, PlatformResponse.Error.INVALID_PERMISSION, "Permission is not enough to delete this context");
        }

        contextRepository.delete(toGet.getId());

        return PlatformResponse.ok();
    }

    private ContextDTO convertToDTO(Context entity, boolean isRules){
        return new ContextDTO(
                entity.getId(),
                entity.getName(),
                entity.getUserId(),
                entity.getDate(),
                entity.getLastUpdate(),
                isRules ? convertRulesToDTO(entity.getRules(), false) : null
        );
    }

    private List<ContextDTO> convertToDTO(List<Context> entities){
        List<ContextDTO> contextsDTO = Lists.newArrayList();
        for (Context context: entities){
            contextsDTO.add(convertToDTO(context, false));
        }

        return contextsDTO;
    }

    private List<RuleDTO> convertRulesToDTO(List<Rule> entities, boolean isContexts){
        List<RuleDTO> rulesDTO = Lists.newArrayList();
        for (Rule rule: entities){
            rulesDTO.add(convertRuleToDTO(rule, isContexts));
        }

        return rulesDTO;
    }

    private RuleDTO convertRuleToDTO(Rule entity, boolean isContexts){
        return new RuleDTO(
                entity.getId(),
                entity.getName(),
                entity.getRule(),
                entity.getType(),
                entity.getUserId(),
                entity.getDate(),
                entity.getLastUpdate(),
                isContexts ? entity.getContexts() : null
        );
    }
}
