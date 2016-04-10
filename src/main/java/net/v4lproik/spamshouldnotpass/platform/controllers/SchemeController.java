package net.v4lproik.spamshouldnotpass.platform.controllers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import net.v4lproik.spamshouldnotpass.platform.models.BasicMember;
import net.v4lproik.spamshouldnotpass.platform.models.SchemeType;
import net.v4lproik.spamshouldnotpass.platform.models.dto.*;
import net.v4lproik.spamshouldnotpass.platform.models.entities.Scheme;
import net.v4lproik.spamshouldnotpass.platform.models.response.PlatformResponse;
import net.v4lproik.spamshouldnotpass.platform.models.response.SchemeResponse;
import net.v4lproik.spamshouldnotpass.platform.repositories.CacheSessionRepository;
import net.v4lproik.spamshouldnotpass.platform.repositories.SchemesRepository;
import net.v4lproik.spamshouldnotpass.platform.services.SchemeService;
import net.v4lproik.spamshouldnotpass.spring.annotation.UserAccess;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Controller
@RequestMapping(value = "/scheme")
public class SchemeController {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private SchemeService schemeService;

    @Autowired
    private SchemesRepository schemesRepository;

    @UserAccess
    @RequestMapping(value = "/create/document", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public PlatformResponse createDocumentModel(HttpServletRequest req, @RequestBody String str) throws IOException {

        final Properties properties = objectMapper.readValue(str, Properties.class);
        final SchemeType type = SchemeType.SPAM;
        final UUID userId = ((BasicMember) req.getAttribute(CacheSessionRepository.MEMBER_KEY)).getId();
        final Map<String, List<PropertyJSON>> mapPropertiesJSON = toMapJSON(properties);


        if(!schemeService.isSchemeValid(toMap(properties))){
            return new SchemeResponse(PlatformResponse.Status.NOK, PlatformResponse.Error.INVALID_INPUT, "The scheme cannot be validated");
        }

        return toSave(userId, type, mapPropertiesJSON);
    }

    @UserAccess
    @RequestMapping(value = "/create/user", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public PlatformResponse createUserModel(HttpServletRequest req, @RequestBody String str) throws IOException {

        final Properties properties = objectMapper.readValue(str, Properties.class);
        final SchemeType type = SchemeType.SPAMMER;
        final UUID userId = ((BasicMember) req.getAttribute(CacheSessionRepository.MEMBER_KEY)).getId();
        final Map<String, List<PropertyJSON>> mapPropertiesJSON = toMapJSON(properties);

        if(!schemeService.isSchemeValid(toMap(properties))){
            return new SchemeResponse(PlatformResponse.Status.NOK, PlatformResponse.Error.INVALID_INPUT, "The scheme cannot be validated");
        }

        return toSave(userId, type, mapPropertiesJSON);
    }

    private SchemeResponse toSave(final UUID userId, final SchemeType type, final Map<String, List<PropertyJSON>> mapPropertiesJSON) throws IOException {
        Scheme created = schemesRepository.listByUserIdAndType(userId, type);

        if (created != null){
            created.setLastUpdate(DateTime.now());
            created.setProperties(objectMapper.writeValueAsString(mapPropertiesJSON));
            schemesRepository.update(
                    created
            );
        }else{
            created = new Scheme(
                    UUID.randomUUID(),
                    objectMapper.writeValueAsString(mapPropertiesJSON),
                    userId,
                    DateTime.now(),
                    DateTime.now(),
                    type

            );
            schemesRepository.save(
                    created
            );
        }

        return new SchemeResponse(
                convertToDTO(
                        new Scheme(
                                created.getId(),
                                objectMapper.writeValueAsString(mapPropertiesJSON),
                                created.getUserId(),
                                created.getDate(),
                                created.getLastUpdate(),
                                created.getType()
                        )
                )
        );
    }

    @UserAccess
    @RequestMapping(value = "/get/document", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public PlatformResponse getDoc(@RequestBody toGetUserDTO userDTO) throws ClassNotFoundException, IOException {

        final UUID userUUID = userDTO.getId();

        final Scheme scheme = schemesRepository.listByUserIdAndType(userUUID, SchemeType.SPAM);

        if (scheme == null){
            return new SchemeResponse(null);
        }

        return new SchemeResponse(
                convertToDTO(scheme)
        );
    }


    @UserAccess
    @RequestMapping(value = "/get/user", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public PlatformResponse getUser(HttpServletRequest req,
                                  @RequestBody toGetUserDTO userDTO) throws ClassNotFoundException, IOException {

        final UUID userUUID = userDTO.getId();

        final Scheme scheme = schemesRepository.listByUserIdAndType(userUUID, SchemeType.SPAMMER);

        if (scheme == null){
            return new SchemeResponse(null);
        }

        return new SchemeResponse(
                convertToDTO(scheme)
        );
    }


    private Map<String, List<String>> toMap(Properties properties){
        Map<String, List<String>> mapProperties = Maps.newHashMap();

        for (Property property:properties.getProperties()){
            List<String> arr = mapProperties.get(property.getVariableType());

            if (arr == null){
                arr = Lists.newArrayList();
            }

            arr.add(property.getVariableName());

            mapProperties.put(property.getVariableType(), arr);
        }

        return mapProperties;
    }

    private Map<String, List<PropertyJSON>> toMapJSON(Properties properties){
        Map<String, List<PropertyJSON>> mapProperties = Maps.newHashMap();

        for (Property property:properties.getProperties()){
            List<PropertyJSON> arr = mapProperties.get(property.getVariableType());

            if (arr == null){
                arr = Lists.newArrayList();
            }

            arr.add(new PropertyJSON(property.getVariableName(), property.getPosition(), property.isLocked(), property.isProvided()));

            mapProperties.put(property.getVariableType(), arr);
        }

        return mapProperties;
    }

    private List<Property> toList(Map<String, List<PropertyJSON>> mapProperties){
        List<Property> propertiesListTmp = Lists.newArrayList();
        for (Map.Entry<String, List<PropertyJSON>> entry : mapProperties.entrySet()) {
            for (PropertyJSON val:entry.getValue()){
                propertiesListTmp.add(new Property(entry.getKey(), val.getName(), val.getPosition(), val.isLocked(), val.isProvided()));
            }
        }

        return propertiesListTmp;
    }

    private SchemeDTO convertToDTO(Scheme entity) throws IOException {

        Map<String, List<PropertyJSON>> myObjects = objectMapper.readValue(entity.getProperties(), new TypeReference<Map<String, List<PropertyJSON>>>() {});

        return new SchemeDTO(
                entity.getId(),
                objectMapper.writeValueAsString(toList(myObjects)),
                entity.getUserId(),
                entity.getDate(),
                entity.getLastUpdate(),
                entity.getType()
        );
    }
}
