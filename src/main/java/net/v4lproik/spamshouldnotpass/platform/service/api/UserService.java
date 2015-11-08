package net.v4lproik.spamshouldnotpass.platform.service.api;

import net.v4lproik.spamshouldnotpass.platform.dao.api.UserDao;
import net.v4lproik.spamshouldnotpass.platform.models.MemberPermission;
import net.v4lproik.spamshouldnotpass.platform.models.MemberStatus;
import net.v4lproik.spamshouldnotpass.platform.service.api.entities.User;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.UUID;

import static com.google.common.base.Preconditions.checkNotNull;

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
        checkNotNull(email);
        checkNotNull(password);

        final User user = userDao.findByEmail(email);

        if (user == null){
            return user;
        }

        boolean auth;
        try {
            auth = passwordService.validatePassword(password, user.getPassword());
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalArgumentException(String.format("[UserService] Error validating password for user %s", email), e);
        } catch (InvalidKeySpecException e) {
            throw new IllegalArgumentException(String.format("[UserService] Error validating password for user %s", email), e);
        }

        if (!auth){
            return null;
        }

        return user;
    }

    public User save(final String firstname,
                     final String lastname,
                     final String status,
                     final String permission,
                     final String email,
                     final String password
    ) {
        checkNotNull(firstname);
        checkNotNull(lastname);
        checkNotNull(permission);
        checkNotNull(password);
        checkNotNull(status);
        checkNotNull(email);

        if (MemberStatus.fromString(status) == null){
            throw new IllegalArgumentException("[UserService] The member status cannot be empty or null");
        }

        if (MemberPermission.fromString(permission) == null){
            throw new IllegalArgumentException("[UserService] The member permission cannot be empty or null");
        }

        if (isEmailAlreadyTaken(email)){
            throw new IllegalArgumentException(String.format("[UserService] The email %s is already taken", email));
        }

        String passwordGenerated;
        try {
            passwordGenerated = passwordService.generateHash(password);
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalArgumentException("[UserService] Error generating password for new user", e);
        } catch (InvalidKeySpecException e) {
            throw new IllegalArgumentException("[UserService] Error generating password for new user", e);
        }

        User user = new User(
                firstname,
                lastname,
                email,
                String.format("%s.%s", firstname, lastname),
                passwordGenerated,
                MemberStatus.fromString(status),
                MemberPermission.fromString(permission),
                DateTime.now()
        );

        return userDao.save(user);
    }

    public boolean isEmailAlreadyTaken(String email){
        checkNotNull(email);

        return userDao.findByEmail(email) != null;
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
    public User findById(UUID id) {
        return userDao.findById(id);
    }

    @Transactional(readOnly = true)
    public User findByLogin(String email) {
        return null;
    }
}
