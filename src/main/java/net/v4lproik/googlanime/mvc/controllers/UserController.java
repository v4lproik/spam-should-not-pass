package net.v4lproik.googlanime.mvc.controllers;

import net.v4lproik.googlanime.annotation.PrivateAccess;
import net.v4lproik.googlanime.mvc.models.UserResponse;
import net.v4lproik.googlanime.service.api.UserService;
import net.v4lproik.googlanime.service.api.entities.Member;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.session.Session;
import org.springframework.session.SessionRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(value = "/user")
public class UserController {

    static Logger log = Logger.getLogger(UserController.class.getName());

    @Autowired
    private UserService userService;

    @Autowired
    private SessionRepository sessionRepo;

    @RequestMapping(value = "/auth", method = RequestMethod.POST, params = {"login", "password"})
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public UserResponse authenticate(@RequestParam(value = "login", required = true) String login,
                             @RequestParam(value = "password", required = true) String password) {

        log.debug(String.format("/user/auth?login=%s&password=%s", login, password));

        UserResponse response = new UserResponse();
        boolean auth = false;

        Member member = userService.authenticate(login, password);
        if (member == null){
            response.setError("The user cannot be authenticated");
            return response;
        }

        response.setUser(member);

        Session session = sessionRepo.createSession();
        sessionRepo.save(session);

        response.setToken(session.getId());

        return response;
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST, params = {"login", "password"})
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public UserResponse create(@RequestParam(value = "login", required = true) String login,
                               @RequestParam(value = "password", required = true) String password) {

        log.debug(String.format("/user/create?login=%s&password=%s", login, password));

        UserResponse response = new UserResponse();

        Member member = userService.save(login, password);

        if (member != null){
            response.setUser(member);
            return response;
        }

        response.setError("Member has not been created");
        return response;
    }

    @PrivateAccess
    @RequestMapping(value = "/info", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public UserResponse profile() {

        log.debug(String.format("/user/info"));

        UserResponse response = new UserResponse();

        response.setUser("profile");

        return response;
    }
}
