package net.v4lproik.spamshouldnotpass.platform.controllers;

import com.google.common.collect.Lists;
import net.v4lproik.spamshouldnotpass.platform.dao.api.RuleDao;
import net.v4lproik.spamshouldnotpass.platform.dao.repositories.CacheSessionRepository;
import net.v4lproik.spamshouldnotpass.platform.models.BasicMember;
import net.v4lproik.spamshouldnotpass.platform.models.dto.RuleDTO;
import net.v4lproik.spamshouldnotpass.platform.models.dto.toCreateRuleDTO;
import net.v4lproik.spamshouldnotpass.platform.models.dto.toGetRuleDTO;
import net.v4lproik.spamshouldnotpass.platform.models.dto.toUpdateRuleDTO;
import net.v4lproik.spamshouldnotpass.platform.models.entities.Rule;
import net.v4lproik.spamshouldnotpass.platform.models.response.PlatformResponse;
import net.v4lproik.spamshouldnotpass.platform.models.response.RulesResponse;
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
@RequestMapping(value = "/rule")
public class RuleController {

    @Autowired
    private RuleDao ruleDao;

    private static Logger log = Logger.getLogger(RuleController.class.getName());

    @UserAccess
    @RequestMapping(value = "/get", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public RulesResponse list(HttpServletRequest req,
                              @RequestBody toGetRuleDTO toGet) {

        final Rule rule = ruleDao.findById(toGet.getId());

        if (rule == null){
            return new RulesResponse(PlatformResponse.Status.NOK, PlatformResponse.Error.INVALID_INPUT, "The rule does not exist");
        }

        return new RulesResponse(
                Lists.newArrayList(
                        convertToDTO(rule, false)
                )
        );
    }

    @UserAccess
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public RulesResponse delete(HttpServletRequest req,
                                @RequestBody toGetRuleDTO toGet) {

        final Rule rule = ruleDao.findById(toGet.getId());

        if (rule == null){
            return new RulesResponse(PlatformResponse.Status.NOK, PlatformResponse.Error.INVALID_INPUT, "The rule does not exist");
        }

        if (!rule.getUserId().equals(((BasicMember) req.getAttribute(CacheSessionRepository.MEMBER_KEY)).getId())){
            return new RulesResponse(PlatformResponse.Status.NOK, PlatformResponse.Error.INVALID_PERMISSION, "Permission is not enough to delete this rule");
        }

        ruleDao.delete(toGet.getId());

        return new RulesResponse(null);
    }

    @UserAccess
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public RulesResponse update(HttpServletRequest req,
                                @RequestBody toUpdateRuleDTO toUpdate) {

        final Rule rule = ruleDao.findById(toUpdate.getId());

        if (rule == null){
            return new RulesResponse(PlatformResponse.Status.NOK, PlatformResponse.Error.INVALID_INPUT, "The rule does not exist");
        }

        if (!rule.getUserId().equals(((BasicMember) req.getAttribute(CacheSessionRepository.MEMBER_KEY)).getId())){
            return new RulesResponse(PlatformResponse.Status.NOK, PlatformResponse.Error.INVALID_PERMISSION, "Permission is not enough to update this rule");
        }

        rule.setId(toUpdate.getId());
        rule.setName(toUpdate.getName());
        rule.setRule(toUpdate.getRule());
        rule.setType(toUpdate.getType());
        rule.setLastUpdate(DateTime.now());

        ruleDao.update(rule);

        return new RulesResponse(null);
    }


    @UserAccess
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public RulesResponse list(HttpServletRequest req) {

        final UUID userId = ((BasicMember) req.getAttribute(CacheSessionRepository.MEMBER_KEY)).getId();

        List<Rule> rules = ruleDao.listByUserId(userId);

        List<RuleDTO> rulesDTO = Lists.newArrayList();
        for (Rule rule: rules){
            rulesDTO.add(
                    convertToDTO(rule, false)
            );
        }

        return new RulesResponse(rulesDTO);
    }


    @UserAccess
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public RulesResponse create(HttpServletRequest req,
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

        ruleDao.save(create);

        return new RulesResponse(
                Lists.newArrayList(
                        convertToDTO(create, false)
                )
        );
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
