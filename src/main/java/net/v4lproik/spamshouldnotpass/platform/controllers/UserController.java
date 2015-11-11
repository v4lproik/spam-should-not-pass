package net.v4lproik.spamshouldnotpass.platform.controllers;

import net.v4lproik.spamshouldnotpass.platform.dao.repositories.CacheSessionRepository;
import net.v4lproik.spamshouldnotpass.platform.models.BasicMember;
import net.v4lproik.spamshouldnotpass.platform.models.MemberPermission;
import net.v4lproik.spamshouldnotpass.platform.models.MemberStatus;
import net.v4lproik.spamshouldnotpass.platform.models.dto.UserDTO;
import net.v4lproik.spamshouldnotpass.platform.models.entities.User;
import net.v4lproik.spamshouldnotpass.platform.models.response.UserResponse;
import net.v4lproik.spamshouldnotpass.platform.service.UserService;
import net.v4lproik.spamshouldnotpass.spring.annotation.UserAccess;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.session.Session;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

@Controller
@RequestMapping(value = "/user")
public class UserController {

    private static Logger log = Logger.getLogger(UserController.class.getName());

    @Autowired
    private UserService userService;

    @Autowired
    private CacheSessionRepository sessionRepo;

    @RequestMapping(value = "/auth", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public UserResponse authenticate(@RequestBody UserDTO userDTO) {

        final String login = userDTO.getEmail();
        final String password = userDTO.getPassword();

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

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public UserResponse create(@RequestBody UserDTO userDTO) {

        final String email = userDTO.getEmail();
        final String password = userDTO.getPassword();
        final String firstname = userDTO.getFirstname();
        final String lastname = userDTO.getLastname();
        final MemberStatus status = userDTO.getStatus();
        final MemberPermission permission = userDTO.getPermission();

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
    public UserResponse delete(@RequestBody UUID uuid) {

        log.debug(String.format("/user/delete"));

        UserResponse response = new UserResponse();

        userService.delete(uuid);

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

//        response.setUser(req.getAttribute(CacheSessionRepository.MEMBER_KEY));

        return response;
    }
}
