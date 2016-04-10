package net.v4lproik.spamshouldnotpass.platform.controllers;

import com.google.common.collect.Lists;
import net.v4lproik.spamshouldnotpass.platform.models.BasicMember;
import net.v4lproik.spamshouldnotpass.platform.models.dto.RuleDTO;
import net.v4lproik.spamshouldnotpass.platform.models.dto.toCreateRuleDTO;
import net.v4lproik.spamshouldnotpass.platform.models.dto.toGetRuleDTO;
import net.v4lproik.spamshouldnotpass.platform.models.dto.toUpdateRuleDTO;
import net.v4lproik.spamshouldnotpass.platform.models.entities.Rule;
import net.v4lproik.spamshouldnotpass.platform.models.response.PlatformResponse;
import net.v4lproik.spamshouldnotpass.platform.models.response.RulesResponse;
import net.v4lproik.spamshouldnotpass.platform.repositories.CacheSessionRepository;
import net.v4lproik.spamshouldnotpass.platform.repositories.RulesRepository;
import net.v4lproik.spamshouldnotpass.spring.annotation.UserAccess;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Controller
@RequestMapping(value = "/rule")
public class RuleController {

    @Autowired
    private RulesRepository rulesRepository;

    @UserAccess
    @RequestMapping(value = "/get", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public PlatformResponse list(HttpServletRequest req,
                                 @RequestBody toGetRuleDTO toGet) {

        final Optional<Rule> rule = rulesRepository.findById(toGet.getId());

        if (!rule.isPresent()){
            return new RulesResponse(PlatformResponse.Status.NOK, PlatformResponse.Error.INVALID_INPUT, "The rule does not exist");
        }

        return new RulesResponse(
                Lists.newArrayList(
                        convertToDTO(rule.get(), false)
                )
        );
    }

    @UserAccess
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public PlatformResponse list(HttpServletRequest req) {

        final UUID userId = ((BasicMember) req.getAttribute(CacheSessionRepository.MEMBER_KEY)).getId();

        List<Rule> rules = rulesRepository.listByUserId(userId);

        List<RuleDTO> rulesDTO = Lists.newArrayList();
        for (Rule rule: rules){
            rulesDTO.add(
                    convertToDTO(rule, false)
            );
        }

        return new RulesResponse(rulesDTO);
    }

    @UserAccess
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public PlatformResponse update(HttpServletRequest req,
                                   @RequestBody toUpdateRuleDTO toUpdate) {

        final Optional<Rule> rule = rulesRepository.findById(toUpdate.getId());

        if (!rule.isPresent()){
            return new RulesResponse(PlatformResponse.Status.NOK, PlatformResponse.Error.INVALID_INPUT, "The rule does not exist");
        }

        final UUID userId = rule.get().getUserId();

        if (!userId.equals(((BasicMember) req.getAttribute(CacheSessionRepository.MEMBER_KEY)).getId())){
            return new RulesResponse(PlatformResponse.Status.NOK, PlatformResponse.Error.INVALID_PERMISSION, "Permission is not enough to update this rule");
        }

        Rule modifiedRule = rule.get();
        modifiedRule.setId(toUpdate.getId());
        modifiedRule.setName(toUpdate.getName());
        modifiedRule.setRule(toUpdate.getRule());
        modifiedRule.setType(toUpdate.getType());
        modifiedRule.setLastUpdate(DateTime.now());

        rulesRepository.update(modifiedRule);

        return PlatformResponse.ok();
    }


    @UserAccess
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public PlatformResponse create(HttpServletRequest req,
                                   @RequestBody toCreateRuleDTO toCreate) {

        final UUID userId = ((BasicMember) req.getAttribute(CacheSessionRepository.MEMBER_KEY)).getId();

        Rule create = new Rule(
                toCreate.getId() != null ? toCreate.getId() : UUID.randomUUID(),
                toCreate.getName(),
                toCreate.getRule(),
                toCreate.getType(),
                userId,
                DateTime.now(),
                DateTime.now()
        );

        rulesRepository.save(create);

        return PlatformResponse.ok();
    }

    @UserAccess
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public PlatformResponse delete(HttpServletRequest req,
                                   @RequestBody toGetRuleDTO toGet) {

        final Optional<Rule> rule = rulesRepository.findById(toGet.getId());

        if (!rule.isPresent()){
            return new RulesResponse(PlatformResponse.Status.NOK, PlatformResponse.Error.INVALID_INPUT, "The rule does not exist");
        }

        final UUID userId = rule.get().getUserId();

        if (!userId.equals(((BasicMember) req.getAttribute(CacheSessionRepository.MEMBER_KEY)).getId())){
            return new RulesResponse(PlatformResponse.Status.NOK, PlatformResponse.Error.INVALID_PERMISSION, "Permission is not enough to delete this rule");
        }

        rulesRepository.delete(toGet.getId());

        return PlatformResponse.ok();
    }

    private RuleDTO convertToDTO(Rule entity, boolean isContexts){
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
