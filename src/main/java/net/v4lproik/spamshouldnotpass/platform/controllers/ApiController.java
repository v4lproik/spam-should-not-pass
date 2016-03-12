package net.v4lproik.spamshouldnotpass.platform.controllers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import javassist.CannotCompileException;
import javassist.NotFoundException;
import net.v4lproik.spamshouldnotpass.platform.dao.repositories.*;
import net.v4lproik.spamshouldnotpass.platform.models.BasicMember;
import net.v4lproik.spamshouldnotpass.platform.models.SchemeType;
import net.v4lproik.spamshouldnotpass.platform.models.dto.APIInformationDTO;
import net.v4lproik.spamshouldnotpass.platform.models.dto.PropertyJSON;
import net.v4lproik.spamshouldnotpass.platform.models.dto.toGetApiDTO;
import net.v4lproik.spamshouldnotpass.platform.models.entities.AuthorMessageInfo;
import net.v4lproik.spamshouldnotpass.platform.models.entities.Context;
import net.v4lproik.spamshouldnotpass.platform.models.entities.Rule;
import net.v4lproik.spamshouldnotpass.platform.models.entities.Scheme;
import net.v4lproik.spamshouldnotpass.platform.models.response.PlatformResponse;
import net.v4lproik.spamshouldnotpass.platform.models.response.SpamResponse;
import net.v4lproik.spamshouldnotpass.platform.service.SchemeService;
import net.v4lproik.spamshouldnotpass.spring.annotation.UserAccess;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
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
    private ContextRepository contextRepository;

    @Autowired
    private AuthorInfoRepository authorInfoRepository;

    private static Logger log = Logger.getLogger(ApiController.class.getName());
    private static Map<UUID, DateTime> lastGeneratedTime = Maps.newHashMap();
    private static Map<UUID, Class<?>> lastGeneratedClass = Maps.newHashMap();

    private static String nbDocSubmittedLast5Min = "nbDocSubmittedLast5Min";
    private static String nbSameDocSubmittedLast5Min = "nbSameDocSubmittedLast5Min";

    @UserAccess
    @RequestMapping(value = "/check", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public PlatformResponse check(HttpServletRequest req, @RequestBody toGetApiDTO toGet) throws Exception {

        final BasicMember basicMember = (BasicMember) req.getAttribute(CacheSessionRepository.MEMBER_KEY);

        final UUID userId = basicMember.getId();
        final String corporation = basicMember.getCorporation();

        boolean spam = false;
        String reason = null;

        //grab info given by the user
        Map<String, String> userInformation = getMap(toGet.getInformation());
        completeUserInformation(userInformation, corporation);
        if (userInformation.isEmpty()){
            return new SpamResponse(PlatformResponse.Status.NOK, PlatformResponse.Error.INVALID_INPUT, "Information is missing or invalid");
        }

        //grab context given by the user
        final String contextName = toGet.getContext();
        if (contextName == null){
            return new SpamResponse(PlatformResponse.Status.NOK, PlatformResponse.Error.INVALID_INPUT, "The context is missing or invalid");
        }

        //check if there are rules bound to this context and if the user has enough permission
        final Context context = contextRepository.findByIdWithRules(UUID.fromString(contextName));
        if (context == null){
            return new SpamResponse(PlatformResponse.Status.NOK, PlatformResponse.Error.INVALID_INPUT, "The context is missing or invalid");
        }
        if (!context.getUserId().equals(userId)){
            return new SpamResponse(PlatformResponse.Status.NOK, PlatformResponse.Error.INVALID_PERMISSION, "You don't have the permission to query this context");
        }
        final List<Rule> rules = context.getRules();
        if (rules.size() == 0){
            return new SpamResponse(PlatformResponse.Status.NOK, PlatformResponse.Error.INVALID_INPUT, "This context has an emtpy rules set");
        }

        //grab the scheme
        final List<Scheme> scheme = schemesRepository.listByUserId(userId);
        if (scheme == null){
            log.error(String.format("[ApiController] No scheme repository available for user %s and type %s", userId, SchemeType.SPAM));
            return new SpamResponse(PlatformResponse.Status.NOK, PlatformResponse.Error.INVALID_INPUT, "The scheme is missing or invalid");
        }

        if (scheme.size() != 2){
            log.error(String.format("[ApiController] Scheme list size should not be bigger than 2 for user %s", userId));
            return new SpamResponse(PlatformResponse.Status.NOK, PlatformResponse.Error.INVALID_INPUT, "The scheme is missing or invalid");
        }

        final Scheme userScheme = scheme.get(0).getType().equals(SchemeType.SPAMMER) ? scheme.get(0) : scheme.get(1);
        final Scheme documentScheme = scheme.get(0).getType().equals(SchemeType.SPAM) ? scheme.get(0) : scheme.get(1);

        //transform the scheme
        final DateTime lastUpdate = userScheme.getLastUpdate();
        Map<String, List<String>> mapProperties = getStringListMap(userScheme, documentScheme);

        final Map<Class<?>, List<String>> mapClass = schemeService.transformProperties(mapProperties);

        final Class<?> clazz = getOrGenerateClass(userId, lastUpdate, mapClass);
        final Object obj = clazz.newInstance();

        for (Map.Entry<Class<?>, List<String>> entry : mapClass.entrySet()) {
            for (String val:entry.getValue()){

                final String value = userInformation.get(val);
                if (value != null) {
                    if (entry.getKey().equals(Integer.class)) {
                        clazz.getMethod(
                                String.format("set%s", Character.toUpperCase(val.charAt(0)) + val.substring(1)), entry.getKey()).invoke(obj, Integer.parseInt(value));

                    } else {
                        clazz.getMethod(
                                String.format("set%s", Character.toUpperCase(val.charAt(0)) + val.substring(1)), entry.getKey()).invoke(obj, value);
                    }
                }
            }
        }

        StandardEvaluationContext contextEv = new StandardEvaluationContext(obj);

        for (Rule rule:rules){
            spam = apply(contextEv, rule.getRule());
            if (spam){
                reason = rule.getName();

                break;
            }
        }

        //store information
        storeInformation(userInformation, corporation);

        return new SpamResponse(String.valueOf(spam), reason);
    }

    /**
     * This function add the information provides by spam slap
     * @param userInformation
     */
    private void completeUserInformation(Map<String, String> userInformation, String corporation) {
        Integer nbOfCommentsLast5Min = authorInfoRepository.getNumberOfDocumentsSubmittedInTheLast5min(userInformation.getOrDefault("email", ""), corporation);
        Integer nbOfSameCommentsLast5Min = authorInfoRepository.getNumberOfSameDocumentsSubmittedInTheLast5min(userInformation.getOrDefault("email", ""), corporation, userInformation.getOrDefault("content", ""));

        userInformation.put(nbDocSubmittedLast5Min, String.valueOf(nbOfCommentsLast5Min));
        userInformation.put(nbSameDocSubmittedLast5Min, String.valueOf(nbOfCommentsLast5Min));
    }

    /**
     * This function stores the information provided by the user
     * @param userInformation
     */
    private void storeInformation(Map<String, String> userInformation, String corporation) {
        authorInfoRepository.store(
                new AuthorMessageInfo(
                        userInformation.getOrDefault("email", ""),
                        corporation,
                        userInformation.getOrDefault("content", ""),
                        Integer.parseInt(userInformation.get(nbDocSubmittedLast5Min)) + 1
                )
        );
    }

    /**
     * This function merges doc/user schemes
     * @param userScheme
     * @param documentScheme
     * @return
     * @throws java.io.IOException
     */
    private Map<String, List<String>> getStringListMap(Scheme userScheme, Scheme documentScheme) throws java.io.IOException {
        Map<String, List<PropertyJSON>> mapUser = objectMapper.readValue(userScheme.getProperties(), new TypeReference<Map<String, List<PropertyJSON>>>() {
        });
        Map<String, List<PropertyJSON>> mapDocument = objectMapper.readValue(documentScheme.getProperties(), new TypeReference<Map<String, List<PropertyJSON>>>() {});
        Map<String, List<String>> mapProperties = Maps.newHashMap();

        for (Map.Entry<String, List<PropertyJSON>> entry : mapUser.entrySet()) {
            List<String> propertiesListTmp = Lists.newArrayList();
            for (PropertyJSON val:entry.getValue()){
                propertiesListTmp.add(val.getName());
            }
            mapProperties.put(entry.getKey(), propertiesListTmp);
        }

        for (Map.Entry<String, List<PropertyJSON>> entry : mapDocument.entrySet()) {
            List<String> propertiesListTmp = mapProperties.get(entry.getKey());

            for (PropertyJSON val:entry.getValue()){
                propertiesListTmp.add(val.getName());
            }
            mapProperties.put(entry.getKey(), propertiesListTmp);
        }


        return mapProperties;
    }

    /**
     * Applying a rule against the object filled of information from doc/user schemes
     * @param context
     * @param rule
     * @return
     */
    private boolean apply(StandardEvaluationContext context, String rule){

        boolean resultRule = false;
        try{
            resultRule = parser.parseExpression(rule).getValue(context, Boolean.class);
        }catch (Exception e){
            e.printStackTrace();
        }
        return resultRule;
    }

    /**
     * This map contains a variableName -> variableValue for spel context
     * @param list
     * @return
     */
    private Map<String, String> getMap(List<APIInformationDTO> list){
        Map<String, String> map = Maps.newHashMap();
        for (APIInformationDTO info:list){
            map.put(info.getKey(), info.getValue());
        }

        return map;
    }

    /**
     * Create a new object each time the schemes have changed otherwise it doesn't
     * @param userId
     * @param lastUpdate
     * @param mapClass
     * @return
     * @throws NotFoundException
     * @throws CannotCompileException
     */
    private Class<?> getOrGenerateClass(final UUID userId, final DateTime lastUpdate, final Map<Class<?>, List<String>> mapClass) throws NotFoundException, CannotCompileException {
        Class<?> clazz = null;

        if (lastGeneratedTime.containsKey(userId)){
            if (lastUpdate.isAfter(lastGeneratedTime.get(userId))){
                clazz = SchemeService.generate(
                        "net.v4lproik.spamshouldnotpass.platform.models.entities.Pojo$Generated" + UUID.randomUUID().toString(), mapClass);
                lastGeneratedClass.put(userId, clazz);
                lastGeneratedTime.put(userId, DateTime.now());
            }else{
                clazz = lastGeneratedClass.get(userId);
            }
        }else{
            clazz = SchemeService.generate(
                    "net.v4lproik.spamshouldnotpass.platform.models.entities.Pojo$Generated" + UUID.randomUUID().toString(), mapClass);
            lastGeneratedClass.put(userId, clazz);
            lastGeneratedTime.put(userId, DateTime.now());
        }

        return clazz;
    }
}
