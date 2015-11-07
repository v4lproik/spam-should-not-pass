package net.v4lproik.spamshouldnotpass.platform.service.api;

import net.v4lproik.spamshouldnotpass.platform.dao.api.UserDao;
import net.v4lproik.spamshouldnotpass.platform.models.MemberPermission;
import net.v4lproik.spamshouldnotpass.platform.models.MemberStatus;
import net.v4lproik.spamshouldnotpass.platform.service.api.entities.User;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.UUID;

@Service
public class UserService {

    private static Logger log = Logger.getLogger(UserService.class);

    private final UserDao userDao;

    private final PasswordService passwordService;

    @Autowired
    public UserService(final UserDao userDao, final PasswordService passwordService) {
        this.userDao = userDao;
        this.passwordService = passwordService;
    }

    public User authenticate(String email, String password){
        User user = null;

        if (email == null || email.isEmpty()){
            log.debug("[UserService] The email cannot be empty or null");
            return user;
        }

        // password policy
        if (password == null || password.isEmpty()){
            log.debug("[UserService] The password cannot be empty or null");
            return user;
        }

        user = userDao.findByEmail(email);

        if (user == null){
            return user;
        }

        boolean auth = false;
        try {
            auth = passwordService.validatePassword(password, user.getPassword());
        } catch (NoSuchAlgorithmException e) {
            log.debug("[UserService] Error generating password to check if user exist in database", e);
            return null;
        } catch (InvalidKeySpecException e) {
            log.debug("[UserService] Error generating password to check if user exist in database", e);
            return null;
        }

        if (auth){
            return user;
        }

        return null;
    }

    public User save(final String email, final String password) {

        User user = new User();

        if (email == null || email.isEmpty()){
            log.debug("[UserService] The email cannot be empty or null");
            return null;
        }

        // password policy
        if (password == null || password.isEmpty()){
            log.debug("[UserService] The password cannot be empty or null");
            return null;
        }

        String passwordGenerated = null;
        try {
            passwordGenerated = passwordService.generateHash(password);
        } catch (NoSuchAlgorithmException e) {
            log.debug("[UserService] Error generating password for new user", e);
            return null;
        } catch (InvalidKeySpecException e) {
            log.debug("[UserService] Error generating password for new user", e);
            return null;
        }

        user.setPassword(passwordGenerated);
        user.setEmail(email);
        user.setPassword(password);
        user.setPermission(MemberPermission.REGULAR);
        user.setStatus(MemberStatus.USER);

        return userDao.save(user);
    }

    @Transactional(readOnly = false)
    public void delete(UUID id) {
        userDao.delete(id);
    }

    @Transactional(readOnly = false)
    public void delete(User user) {
        userDao.delete(user.getId());
    }

    @Transactional(readOnly = true)
    public User findById(String id) {
        return userDao.findById(id);
    }

    @Transactional(readOnly = true)
    public User findByLogin(String email) {
        return null;
    }
}
