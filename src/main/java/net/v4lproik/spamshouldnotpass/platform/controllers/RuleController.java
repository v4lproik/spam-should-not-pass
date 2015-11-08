package net.v4lproik.spamshouldnotpass.platform.controllers;

import net.v4lproik.spamshouldnotpass.platform.dao.api.RuleDao;
import net.v4lproik.spamshouldnotpass.platform.dao.repositories.CacheSessionRepository;
import net.v4lproik.spamshouldnotpass.platform.models.BasicMember;
import net.v4lproik.spamshouldnotpass.platform.models.response.RulesResponse;
import net.v4lproik.spamshouldnotpass.platform.service.api.entities.Rule;
import net.v4lproik.spamshouldnotpass.spring.annotation.UserAccess;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

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
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public RulesResponse list(HttpServletRequest req) {

        log.debug(String.format("/api/v1/list-rule"));

        final UUID userId = ((BasicMember) req.getAttribute(CacheSessionRepository.MEMBER_KEY)).getId();

        List<Rule> rules = ruleDao.listByUserId(userId);

        return new RulesResponse(rules);
    }
}
