package net.v4lproik.spamshouldnotpass.platform.controllers;

import com.google.common.collect.Lists;
import net.v4lproik.spamshouldnotpass.platform.dao.api.ContextDao;
import net.v4lproik.spamshouldnotpass.platform.dao.repositories.CacheSessionRepository;
import net.v4lproik.spamshouldnotpass.platform.models.BasicMember;
import net.v4lproik.spamshouldnotpass.platform.models.dto.*;
import net.v4lproik.spamshouldnotpass.platform.models.entities.CompositePKRulesInContext;
import net.v4lproik.spamshouldnotpass.platform.models.entities.Context;
import net.v4lproik.spamshouldnotpass.platform.models.entities.Rule;
import net.v4lproik.spamshouldnotpass.platform.models.entities.RuleInContext;
import net.v4lproik.spamshouldnotpass.platform.models.response.ContextsResponse;
import net.v4lproik.spamshouldnotpass.platform.models.response.PlatformResponse;
import net.v4lproik.spamshouldnotpass.spring.annotation.UserAccess;
import org.apache.log4j.Logger;
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
    private ContextDao contextDao;

    private static Logger log = Logger.getLogger(ContextController.class.getName());

    @UserAccess
    @RequestMapping(value = "/get", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public ContextsResponse get(HttpServletRequest req,
                                @RequestBody toGetContextDTO toGet) {

        log.debug(String.format("/context/get"));

        final Context context = contextDao.findById(toGet.getId());

        if (context == null){
            return new ContextsResponse(PlatformResponse.Status.NOK, PlatformResponse.Error.INVALID_INPUT, "The context does not exist");
        }

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
    public ContextsResponse getAndRules(@RequestBody toGetContextDTO toGet) {

        log.debug(String.format("/context/get"));

        final Context context = contextDao.findByIdWithRules(toGet.getId());

        if (context == null){
            return new ContextsResponse(PlatformResponse.Status.NOK, PlatformResponse.Error.INVALID_INPUT, "The context does not exist");
        }

        return new ContextsResponse(
                Lists.newArrayList(
                        convertToDTO(context, true)
                )
        );
    }

    @UserAccess
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public ContextsResponse delete(HttpServletRequest req,
                                   @RequestBody toGetContextDTO toGet) {

        log.debug(String.format("/context/delete"));

        final Context context = contextDao.findById(toGet.getId());

        if (context == null){
            return new ContextsResponse(PlatformResponse.Status.NOK, PlatformResponse.Error.INVALID_INPUT, "The context does not exist");
        }

        if (!context.getUserId().equals(((BasicMember) req.getAttribute(CacheSessionRepository.MEMBER_KEY)).getId())){
            return new ContextsResponse(PlatformResponse.Status.NOK, PlatformResponse.Error.INVALID_PERMISSION, "Permission is not enough to delete this context");
        }

        contextDao.delete(toGet.getId());

        return new ContextsResponse(null);
    }

    @UserAccess
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public ContextsResponse delete(HttpServletRequest req,
                                   @RequestBody toCreateRuleDTO toUpdate) {

        log.debug(String.format("/context/update"));

        final Context context = contextDao.findById(toUpdate.getId());

        if (context == null){
            return new ContextsResponse(PlatformResponse.Status.NOK, PlatformResponse.Error.INVALID_INPUT, "The context does not exist");
        }

        if (!context.getUserId().equals(((BasicMember) req.getAttribute(CacheSessionRepository.MEMBER_KEY)).getId())){
            return new ContextsResponse(PlatformResponse.Status.NOK, PlatformResponse.Error.INVALID_PERMISSION, "Permission is not enough to update this context");
        }

        contextDao.update(
                new Context(
                        toUpdate.getId(),
                        toUpdate.getName(),
                        context.getUserId(),
                        context.getDate(),
                        DateTime.now()
                ));

        return new ContextsResponse(null);
    }


    @UserAccess
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public ContextsResponse list(HttpServletRequest req) {

        log.debug(String.format("/context/list"));

        final UUID userId = ((BasicMember) req.getAttribute(CacheSessionRepository.MEMBER_KEY)).getId();

        List<Context> contexts = contextDao.listByUserId(userId);

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
    public ContextsResponse create(HttpServletRequest req,
                                   @RequestBody toCreateContextDTO toCreate) {

        log.debug(String.format("/context/create"));

        final UUID userId = ((BasicMember) req.getAttribute(CacheSessionRepository.MEMBER_KEY)).getId();

        if (contextDao.findByName(toCreate.getName()) != null){
            return new ContextsResponse(PlatformResponse.Status.NOK,
                    PlatformResponse.Error.INVALID_INPUT,
                    String.format("Context's name %s has alredy been created", toCreate.getName()));
        }

        Context context = new Context(
                UUID.randomUUID(),
                toCreate.getName(),
                userId,
                DateTime.now(),
                DateTime.now()
        );

        contextDao.save(context);

        return new ContextsResponse(
                Lists.newArrayList(
                        convertToDTO(context, false)
                )
        );
    }

    @UserAccess
    @RequestMapping(value = "/add-rules", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public ContextsResponse addRules(HttpServletRequest req,
                                     @RequestBody RulesInContextDTO toCreate) {

        log.debug(String.format("/context/add-rules"));

        final Context context = contextDao.findById(toCreate.getIdContext());

        if (context == null){
            return new ContextsResponse(PlatformResponse.Status.NOK, PlatformResponse.Error.INVALID_INPUT, "The context does not exist");
        }

        if (!context.getUserId().equals(((BasicMember) req.getAttribute(CacheSessionRepository.MEMBER_KEY)).getId())){
            return new ContextsResponse(PlatformResponse.Status.NOK, PlatformResponse.Error.INVALID_PERMISSION, "Permission is not enough to update this context");
        }

        for (toGetRuleDTO ruleId:toCreate.getListRules()){
            contextDao.addRule(
                    new RuleInContext(
                            new CompositePKRulesInContext(ruleId.getId(), context.getId())
                    )
            );
        }

        return new ContextsResponse(null);
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
