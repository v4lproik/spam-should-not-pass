package net.v4lproik.spamshouldnotpass.platform.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.v4lproik.spamshouldnotpass.platform.dao.repositories.CacheSessionRepository;
import net.v4lproik.spamshouldnotpass.platform.dao.repositories.RulesRepository;
import net.v4lproik.spamshouldnotpass.platform.dao.repositories.SchemesRepository;
import net.v4lproik.spamshouldnotpass.platform.models.BasicMember;
import net.v4lproik.spamshouldnotpass.platform.models.RuleType;
import net.v4lproik.spamshouldnotpass.platform.models.SchemeType;
import net.v4lproik.spamshouldnotpass.platform.models.entities.Rule;
import net.v4lproik.spamshouldnotpass.platform.models.entities.Scheme;
import net.v4lproik.spamshouldnotpass.platform.models.response.SpamResponse;
import net.v4lproik.spamshouldnotpass.platform.service.SchemeService;
import net.v4lproik.spamshouldnotpass.spring.annotation.UserAccess;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Controller
@RequestMapping(value = "/api/v1")
public class ApiController {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ExpressionParser parser;

    @Autowired
    private SchemeService schemeService;

    @Autowired
    private SchemesRepository schemesRepository;

    @Autowired
    private RulesRepository rulesRepository;

    private static Logger log = Logger.getLogger(ApiController.class.getName());

    @UserAccess
    @RequestMapping(value = "/check-comment", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public SpamResponse checkComment(HttpServletRequest req,
                                     @RequestBody Map<String, String> values) throws Exception {

        log.debug(String.format("/api/v1/check-comment?values=%s", values.toString()));

        final UUID userId = ((BasicMember) req.getAttribute(CacheSessionRepository.MEMBER_KEY)).getId();

        boolean spam = false;
        String reason = null;

        //grab the scheme
        final Scheme scheme = schemesRepository.listByUserIdAndType(userId, SchemeType.SPAM);

        if (scheme == null){
            throw new Exception(String.format("[ApiController] No scheme repository available for user %s and type %s", userId, SchemeType.SPAM));
        }

        Map<String, List<String>> map = objectMapper.readValue(scheme.getProperties(), Map.class);

        Map<Class<?>, List<String>> mapClass = schemeService.transformProperties(map);

        Class<?> clazz = SchemeService.generate(
                "net.v4lproik.spamshouldnotpass.platform.models.entities.Pojo$Generated", mapClass);

        Object obj = clazz.newInstance();

        mapClass.entrySet()
                .parallelStream()
                .forEach(x -> {
                            for (String variableName:x.getValue()){
                                try {
                                    clazz.getMethod(String.format("set%s", Character.toUpperCase(variableName.charAt(0)) + variableName.substring(1)), x.getKey()).invoke(obj, values.get(variableName));
                                } catch (IllegalAccessException e) {
                                    e.printStackTrace();
                                } catch (InvocationTargetException e) {
                                    e.printStackTrace();
                                } catch (NoSuchMethodException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                );

        StandardEvaluationContext context = new StandardEvaluationContext(obj);

        final List<Rule> rules = rulesRepository.listByUserIdAndType(userId, RuleType.SPAM);

        for (Rule rule:rules){
            spam = apply(context, rule.getRule());
            if (spam){
                reason = rule.getName();

                break;
            }
        }

        return new SpamResponse(String.valueOf(spam), reason);
    }

    @UserAccess
    @RequestMapping(value = "/check-user", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public SpamResponse checkUser(HttpServletRequest req,
                                     @RequestBody Map<String, String> values) throws Exception {

        log.debug(String.format("/api/v1/check-user?values=%s", values.toString()));

        final UUID userId = ((BasicMember) req.getAttribute(CacheSessionRepository.MEMBER_KEY)).getId();

        boolean spam = false;
        String reason = null;

        //grab the scheme
        final Scheme scheme = schemesRepository.listByUserIdAndType(userId, SchemeType.SPAMMER);

        if (scheme == null){
            throw new Exception(String.format("[ApiController] No scheme repository available for user %s and type %s", userId, SchemeType.SPAM));
        }

        Map<String, List<String>> map = objectMapper.readValue(scheme.getProperties(), Map.class);

        Map<Class<?>, List<String>> mapClass = schemeService.transformProperties(map);

        Class<?> clazz = SchemeService.generate(
                "net.v4lproik.spamshouldnotpass.platform.models.entities.Pojo$Generated", mapClass);

        Object obj = clazz.newInstance();

        mapClass.entrySet()
                .parallelStream()
                .forEach(x -> {
                            for (String variableName:x.getValue()){
                                try {
                                    clazz.getMethod(String.format("set%s", Character.toUpperCase(variableName.charAt(0)) + variableName.substring(1)), x.getKey()).invoke(obj, values.get(variableName));
                                } catch (IllegalAccessException e) {
                                    e.printStackTrace();
                                } catch (InvocationTargetException e) {
                                    e.printStackTrace();
                                } catch (NoSuchMethodException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                );

        StandardEvaluationContext context = new StandardEvaluationContext(obj);

        final List<Rule> rules = rulesRepository.listByUserIdAndType(userId, RuleType.SPAMMER);

        for (Rule rule:rules){
            spam = apply(context, rule.getRule());
            if (spam){
                reason = rule.getName();

                break;
            }
        }

        return new SpamResponse(String.valueOf(spam), reason);
    }


    @UserAccess
    @RequestMapping(value = "/submit-comment-as-spam", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public SpamResponse submitCommentAsSpam(HttpServletRequest req,
                                     @RequestBody Map<String, String> values) throws Exception {

        log.debug(String.format("/api/v1/submit-comment-as-spam?values=%s", values.toString()));

        final UUID userId = ((BasicMember) req.getAttribute(CacheSessionRepository.MEMBER_KEY)).getId();

        boolean spam = false;
        String reason = null;

        //grab the scheme
        final Scheme scheme = schemesRepository.listByUserIdAndType(userId, SchemeType.SPAM);

        if (scheme == null){
            throw new Exception(String.format("[ApiController] No scheme repository available for user %s and type %s", userId, SchemeType.SPAM));
        }

        Map<String, List<String>> map = objectMapper.readValue(scheme.getProperties(), Map.class);

        Map<Class<?>, List<String>> mapClass = schemeService.transformProperties(map);

        Class<?> clazz = SchemeService.generate(
                "net.v4lproik.spamshouldnotpass.platform.models.entities.Pojo$Generated", mapClass);

        Object obj = clazz.newInstance();

        mapClass.entrySet()
                .parallelStream()
                .forEach(x -> {
                            for (String variableName:x.getValue()){
                                try {
                                    clazz.getMethod(String.format("set%s", Character.toUpperCase(variableName.charAt(0)) + variableName.substring(1)), x.getKey()).invoke(obj, values.get(variableName));
                                } catch (IllegalAccessException e) {
                                    e.printStackTrace();
                                } catch (InvocationTargetException e) {
                                    e.printStackTrace();
                                } catch (NoSuchMethodException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                );

        StandardEvaluationContext context = new StandardEvaluationContext(obj);

        final List<Rule> rules = rulesRepository.listByUserIdAndType(userId, RuleType.SPAM);

        for (Rule rule:rules){
            spam = apply(context, rule.getRule());
            if (spam){
                reason = rule.getName();

                break;
            }
        }

        return new SpamResponse(String.valueOf(spam), reason);
    }

    private boolean apply(StandardEvaluationContext context, String rule){

        boolean resultRule = false;
        try{
            resultRule = parser.parseExpression(rule).getValue(context, Boolean.class);
        }catch (Exception e){
            e.printStackTrace();
        }
        return resultRule;
    }
}