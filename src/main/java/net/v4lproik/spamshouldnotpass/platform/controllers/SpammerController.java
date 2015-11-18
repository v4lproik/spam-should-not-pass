package net.v4lproik.spamshouldnotpass.platform.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.v4lproik.spamshouldnotpass.platform.dao.repositories.CacheSessionRepository;
import net.v4lproik.spamshouldnotpass.platform.dao.repositories.SchemesRepository;
import net.v4lproik.spamshouldnotpass.platform.models.BasicMember;
import net.v4lproik.spamshouldnotpass.platform.models.SchemeType;
import net.v4lproik.spamshouldnotpass.platform.models.entities.Scheme;
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
import java.util.Map;
import java.util.UUID;

@Controller
@RequestMapping(value = "/spammer")
public class SpammerController {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private SchemeService schemeService;

    @Autowired
    private SchemesRepository schemesRepository;

    private static Logger log = Logger.getLogger(SpammerController.class.getName());

    @UserAccess
    @RequestMapping(value = "/create-spammer-document", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public SchemeResponse createSpammerDocument(HttpServletRequest req,
                                                @RequestBody Map<String, String> mapScheme) throws ClassNotFoundException, JsonProcessingException {

        log.debug(String.format("/spammer/create-spammer-document?%s", mapScheme));

        final UUID userId = ((BasicMember) req.getAttribute(CacheSessionRepository.MEMBER_KEY)).getId();

//        if(!schemeService.isSchemeValid(mapScheme)){
//            return new SchemeResponse(PlatformResponse.Status.NOK, PlatformResponse.Error.INVALID_INPUT, "The scheme cannot be validated");
//        }

        final Scheme scheme = new Scheme(
                UUID.randomUUID(),
                objectMapper.writeValueAsString(mapScheme),
                userId,
                DateTime.now(),
                DateTime.now(),
                SchemeType.SPAM
        );

        schemesRepository.save(
                scheme
        );

        return new SchemeResponse(scheme);
    }
}
