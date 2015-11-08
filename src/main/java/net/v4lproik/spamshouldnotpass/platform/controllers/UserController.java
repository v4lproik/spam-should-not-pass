package net.v4lproik.spamshouldnotpass.platform.controllers;

import net.v4lproik.spamshouldnotpass.platform.dao.repositories.CacheSessionRepository;
import net.v4lproik.spamshouldnotpass.platform.models.BasicMember;
import net.v4lproik.spamshouldnotpass.platform.models.UserResponse;
import net.v4lproik.spamshouldnotpass.platform.service.api.UserService;
import net.v4lproik.spamshouldnotpass.platform.service.api.entities.User;
import net.v4lproik.spamshouldnotpass.spring.annotation.UserAccess;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.session.Session;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping(value = "/user")
public class UserController {

    private static Logger log = Logger.getLogger(UserController.class.getName());

    @Autowired
    private UserService userService;

    @Autowired
    private CacheSessionRepository sessionRepo;

    @RequestMapping(value = "/auth", method = RequestMethod.POST, params = {"login", "password"})
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public UserResponse authenticate(@RequestParam(value = "login", required = true) String login,
                                     @RequestParam(value = "password", required = true) String password) {

        log.debug(String.format("/user/auth?login=%s&password=%s", login, password));

        UserResponse response = new UserResponse();

        User user = userService.authenticate(login, password);
        if (user == null){
            response.setError("The user cannot be authenticated");
            return response;
        }

        response.setUser(user);

        Session session = sessionRepo.createSession();
        BasicMember basicMember = new BasicMember(user.getId(), user.getEmail(), user.getNickname(), user.getStatus(), user.getPermission());
        session.setAttribute(CacheSessionRepository.MEMBER_KEY, basicMember);
        sessionRepo.save(session);

        response.setToken(session.getId());

        return response;
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST, params = {"firstname", "lastname", "email", "password", "status", "permission"})
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public UserResponse create(@RequestParam(value = "firstname", required = true) String firstname,
                               @RequestParam(value = "lastname", required = true) String lastname,
                               @RequestParam(value = "email", required = true) String email,
                               @RequestParam(value = "password", required = true) String password,
                               @RequestParam(value = "status", required = true) String status,
                               @RequestParam(value = "permission", required = true) String permission) {

        log.debug(String.format("/user/create?email=%s&password=%s", email, password));

        UserResponse response = new UserResponse();

        if (userService.isEmailAlreadyTaken(email)){
            response.setError("Email is already taken");
            return response;
        }

        User created = userService.save(
                firstname,
                lastname,
                status,
                permission,
                email,
                password
        );

        if (created == null){
            response.setError("User has not been created");
            return response;
        }

        response.setUser(created);
        return response;
    }

    @UserAccess
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public UserResponse delete(HttpServletRequest req) {

        log.debug(String.format("/user/delete"));

        UserResponse response = new UserResponse();

        BasicMember member = (BasicMember) req.getAttribute(CacheSessionRepository.MEMBER_KEY);

        userService.delete(member.getId());

        response.setError("User has been deleted");
        return response;
    }

    @UserAccess
    @RequestMapping(value = "/info", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public UserResponse profile(HttpServletRequest req) {

        log.debug(String.format("/user/info"));

        UserResponse response = new UserResponse();

        response.setUser(req.getAttribute(CacheSessionRepository.MEMBER_KEY));

        return response;
    }
}
