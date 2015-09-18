package net.v4lproik.googlanime.mvc.controllers;

import net.v4lproik.googlanime.mvc.models.AnimeResponse;
import net.v4lproik.googlanime.mvc.models.Website;
import net.v4lproik.googlanime.service.api.AnimeServiceRead;
import net.v4lproik.googlanime.service.api.entities.Entry;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.session.SessionRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping(value = "/animes")
public class AnimeController {

    static Logger log = Logger.getLogger(AnimeController.class.getName());

    @Autowired
    private AnimeServiceRead service;

    @Autowired
    private HttpServletRequest req;

    @Autowired
    private SessionRepository sessionRepo;

    @RequestMapping(value = "", method = RequestMethod.GET, params = {"query", "fields", "type", "render"})
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public AnimeResponse list(@RequestParam(value = "query", required = true) String query,
                             @RequestParam(value = "type", required = true) String[] type,
                             @RequestParam(value = "fields", required = true) String[] fields,
                             @RequestParam(value = "render", required = false) String render) throws IOException {

        log.debug(String.format("/animes?query=%s&fields=%s&type=%s&render=%s", query, Arrays.asList(fields), Arrays.asList(type), render));

        Class<?> toCast = Entry.class;
        AnimeResponse response = new AnimeResponse();

        if (Website.containsValue(render) != null){
            switch (Website.containsValue(render)) {
                case MAL:
                    toCast = Entry.class;
                    break;
            }
        }

        try {
            List<?> animes = service.find(query, type, fields, toCast);
            response.setAnimes(animes);
        } catch (Exception e) {
            log.error("Unable to find any entry : " + e);
            response.setError(e.getMessage());
        }

        return response;
    }
}
