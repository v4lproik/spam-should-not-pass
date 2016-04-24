package net.v4lproik.spamshouldnotpass.platform.controllers;

import net.v4lproik.spamshouldnotpass.platform.models.BasicMember;
import net.v4lproik.spamshouldnotpass.platform.models.MemberPermission;
import net.v4lproik.spamshouldnotpass.platform.models.MemberStatus;
import net.v4lproik.spamshouldnotpass.platform.models.PlatformException;
import net.v4lproik.spamshouldnotpass.platform.models.dto.BasicUserDTO;
import net.v4lproik.spamshouldnotpass.platform.models.dto.toAuthUserDTO;
import net.v4lproik.spamshouldnotpass.platform.models.dto.toCreateUserDTO;
import net.v4lproik.spamshouldnotpass.platform.models.entities.User;
import net.v4lproik.spamshouldnotpass.platform.models.response.ApiKeyResponse;
import net.v4lproik.spamshouldnotpass.platform.models.response.BasicUserResponse;
import net.v4lproik.spamshouldnotpass.platform.models.response.PlatformResponse;
import net.v4lproik.spamshouldnotpass.platform.models.response.UserResponse;
import net.v4lproik.spamshouldnotpass.platform.repositories.CacheSessionRepository;
import net.v4lproik.spamshouldnotpass.platform.repositories.UserRepository;
import net.v4lproik.spamshouldnotpass.platform.services.ApiKeyService;
import net.v4lproik.spamshouldnotpass.platform.services.UserService;
import net.v4lproik.spamshouldnotpass.spring.annotation.UserAccess;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.session.Session;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Optional;
import java.util.UUID;

@Controller
@RequestMapping(value = "/user")
public class UserController {

    private static Logger log = Logger.getLogger(UserController.class.getName());

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ApiKeyService apiKeyService;

    @Autowired
    private CacheSessionRepository sessionRepo;

    @RequestMapping(value = "/auth", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public PlatformResponse authenticate(@RequestBody toAuthUserDTO toAuthUserDTO) {

        final String login = toAuthUserDTO.getEmail();
        final String password = toAuthUserDTO.getPassword();

        User user = userService.authenticate(login, password);
        if (user == null){
            return new UserResponse(PlatformResponse.Status.NOK, PlatformResponse.Error.INVALID_INPUT, "Invalid Login or Password");
        }

        Session session = sessionRepo.createSession();
        BasicMember basicMember = new BasicMember(user.getId(), user.getEmail(), user.getNickname(), user.getStatus(), user.getPermission(), user.getCorporation());
        session.setAttribute(CacheSessionRepository.MEMBER_KEY, basicMember);
        sessionRepo.save(session);

        return new UserResponse(
                convertUserToBasicUserDTO(user),
                session.getId()
        );
    }

    @UserAccess
    @RequestMapping(value = "/info", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public PlatformResponse profile(HttpServletRequest req) {

        final BasicMember user = ((BasicMember) req.getAttribute(CacheSessionRepository.MEMBER_KEY));

        return new BasicUserResponse(
                convertBasicUserToDTO(user)
        );
    }

    @UserAccess
    @RequestMapping(value = "/get-api-key", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public PlatformResponse getApiKey(HttpServletRequest req) {

        final BasicMember basicUser = ((BasicMember) req.getAttribute(CacheSessionRepository.MEMBER_KEY));
        final Optional<User> user = userService.findById(basicUser.getId());

        if (!user.isPresent()){
            return new UserResponse(PlatformResponse.Status.NOK, PlatformResponse.Error.NOT_FOUND, "User can't be found");
        }

        return new ApiKeyResponse(
                user.get().getApiKey()
        );
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public PlatformResponse create(@RequestBody toCreateUserDTO toCreateUserDTO) throws PlatformException {

        final String email = toCreateUserDTO.getEmail();
        final String password = toCreateUserDTO.getPassword();
        final String firstname = toCreateUserDTO.getFirstname();
        final String lastname = toCreateUserDTO.getLastname();
        final MemberStatus status = toCreateUserDTO.getStatus();
        final MemberPermission permission = toCreateUserDTO.getPermission();
        final String corporation = toCreateUserDTO.getCorporation();

        if (userService.isEmailAlreadyTaken(email)){
            throw new PlatformException(PlatformResponse.Error.INVALID_INPUT, "Email is already taken");
        }

        User created = userService.save(
                firstname,
                lastname,
                status,
                permission,
                email,
                password,
                corporation
        );

        if (created == null){
            return new UserResponse(PlatformResponse.Status.NOK, PlatformResponse.Error.UNKNOWN, "User has not been created");
        }

        return new UserResponse(
                convertUserToBasicUserDTO(created),
                null
        );
    }

    @UserAccess
    @RequestMapping(value = "/create-api-key", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public PlatformResponse create_api_key(HttpServletRequest req) {

        final UUID userId = ((BasicMember) req.getAttribute(CacheSessionRepository.MEMBER_KEY)).getId();
        final String apiKey = apiKeyService.generate();

        userRepository.saveApiKey(userId, apiKey);

        return PlatformResponse.ok();
    }

    @UserAccess
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public PlatformResponse delete(HttpServletRequest req, @RequestBody UUID uuid) {

        userService.delete(uuid);
        invalidateSession(req);

        return PlatformResponse.ok();
    }

    @UserAccess
    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public PlatformResponse logout(HttpServletRequest req) {

        invalidateSession(req);

        return PlatformResponse.ok();
    }

    private void invalidateSession(HttpServletRequest req) {
        //invalidate session and remove it from cache
        final HttpSession session = req.getSession();
        if (session != null){
            session.invalidate();
        }
        sessionRepo.delete(req.getHeader("x-auth-token"));
    }

    private BasicUserDTO convertUserToBasicUserDTO(User entity){
        return new BasicUserDTO(
                entity.getId(),
                entity.getEmail(),
                entity.getNickname(),
                entity.getStatus(),
                entity.getPermission()
        );
    }

    private BasicUserDTO convertBasicUserToDTO(BasicMember entity){
        return new BasicUserDTO(
                entity.getId(),
                entity.getEmail(),
                entity.getNickName(),
                entity.getStatus(),
                entity.getPermission()
        );
    }
}
