package net.v4lproik.spamshouldnotpass.platform.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import net.v4lproik.spamshouldnotpass.platform.dao.repositories.CacheSessionRepository;
import net.v4lproik.spamshouldnotpass.platform.dao.repositories.SchemesRepository;
import net.v4lproik.spamshouldnotpass.platform.models.BasicMember;
import net.v4lproik.spamshouldnotpass.platform.models.SchemeType;
import net.v4lproik.spamshouldnotpass.platform.models.dto.Properties;
import net.v4lproik.spamshouldnotpass.platform.models.dto.Property;
import net.v4lproik.spamshouldnotpass.platform.models.dto.SchemeDTO;
import net.v4lproik.spamshouldnotpass.platform.models.dto.toGetUserDTO;
import net.v4lproik.spamshouldnotpass.platform.models.entities.Scheme;
import net.v4lproik.spamshouldnotpass.platform.models.response.PlatformResponse;
import net.v4lproik.spamshouldnotpass.platform.models.response.SchemeResponse;
import net.v4lproik.spamshouldnotpass.platform.service.SchemeService;
import net.v4lproik.spamshouldnotpass.spring.annotation.UserAccess;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Controller
@RequestMapping(value = "/spam")
public class SpamController {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private SchemeService schemeService;

    @Autowired
    private SchemesRepository schemesRepository;

    private static Logger log = Logger.getLogger(SpammerController.class.getName());

    @UserAccess
    @RequestMapping(value = "/create-spam-document", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public SchemeResponse createSpamDocument(HttpServletRequest req, @RequestBody String str) throws IOException {

        log.debug(String.format("/create-spam-document?%s", str));

        final Properties properties = objectMapper.readValue(str, Properties.class);
        final SchemeType type = SchemeType.SPAM;
        final UUID userId = ((BasicMember) req.getAttribute(CacheSessionRepository.MEMBER_KEY)).getId();
        final Map<String, List<String>> mapProperties = toMap(properties);


        if(!schemeService.isSchemeValid(mapProperties)){
            return new SchemeResponse(PlatformResponse.Status.NOK, PlatformResponse.Error.INVALID_INPUT, "The scheme cannot be validated");
        }

        Scheme created = schemesRepository.listByUserIdAndType(userId, type);

        if (created != null){
            created.setLastUpdate(DateTime.now());
            created.setProperties(objectMapper.writeValueAsString(toList(mapProperties)));
            schemesRepository.update(
                    created
            );
        }else{
            created = new Scheme(
                    UUID.randomUUID(),
                    objectMapper.writeValueAsString(toList(mapProperties)),
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
                                objectMapper.writeValueAsString(toList(mapProperties)),
                                created.getUserId(),
                                created.getDate(),
                                created.getLastUpdate(),
                                created.getType()
                        )
                )
        );
    }


    @UserAccess
    @RequestMapping(value = "/all", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public SchemeResponse getAll(HttpServletRequest req,
                                 @RequestBody toGetUserDTO userDTO) throws ClassNotFoundException, IOException {

        log.debug(String.format("/spam/all?%s", userDTO));

        final UUID userUUID = userDTO.getId();

        final Scheme scheme = schemesRepository.listByUserIdAndType(userUUID, SchemeType.SPAM);

        if (scheme == null){
            return new SchemeResponse(null);
        }

        return new SchemeResponse(
                convertToDTO(scheme)
        );
    }

    @RequestMapping(value = "/test", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public SchemeResponse test() throws ClassNotFoundException, JsonProcessingException {

        Map<String, List<String>> properties;

        properties = Maps.newHashMap(ImmutableMap.of("String", Arrays.asList("variable", "vriable2")));

        Scheme created = new Scheme(
                UUID.randomUUID(),
                objectMapper.writeValueAsString(properties),
                UUID.randomUUID(),
                DateTime.now(),
                DateTime.now(),
                SchemeType.SPAM
        );


        return new SchemeResponse(
                convertToDTO(created)
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

    private List<Property> toList(Map<String, List<String>> mapProperties){
        List<Property> propertiesListTmp = Lists.newArrayList();
        for (Map.Entry<String, List<String>> entry : mapProperties.entrySet()) {
            for (String val:entry.getValue()){
                propertiesListTmp.add(new Property(entry.getKey(), val));
            }
        }

        return propertiesListTmp;
    }

    private SchemeDTO convertToDTO(Scheme entity){
        return new SchemeDTO(
                entity.getId(),
                entity.getProperties(),
                entity.getUserId(),
                entity.getDate(),
                entity.getLastUpdate(),
                entity.getType()
        );
    }
}
