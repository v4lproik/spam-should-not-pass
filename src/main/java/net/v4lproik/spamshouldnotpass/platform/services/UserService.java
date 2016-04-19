package net.v4lproik.spamshouldnotpass.platform.services;

import net.v4lproik.spamshouldnotpass.platform.models.MemberPermission;
import net.v4lproik.spamshouldnotpass.platform.models.MemberStatus;
import net.v4lproik.spamshouldnotpass.platform.models.entities.User;
import net.v4lproik.spamshouldnotpass.platform.repositories.UserRepository;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Optional;
import java.util.UUID;

import static com.google.common.base.Preconditions.checkNotNull;

@Service
public class UserService {

    private static Logger log = Logger.getLogger(UserService.class);

    private final UserRepository userRepository;
    private final PasswordService passwordService;

    @Autowired
    public UserService(final UserRepository userRepository, final PasswordService passwordService) {
        this.userRepository = userRepository;
        this.passwordService = passwordService;
    }

    public User authenticate(String email, String password){
        checkNotNull(email);
        checkNotNull(password);

        final Optional<User> user = userRepository.findByEmail(email);

        if (!user.isPresent()){
            return null;
        }

        final String userPassword = user.get().getPassword();
        boolean auth;
        try {
            auth = passwordService.validatePassword(password,userPassword);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new IllegalArgumentException(String.format("[UserService] Error validating password for user %s", email), e);
        }

        if (!auth){
            return null;
        }

        return user.get();
    }

    public User save(final String firstname,
                     final String lastname,
                     final MemberStatus status,
                     final MemberPermission permission,
                     final String email,
                     final String password,
                     final String corporation
    ) {
        checkNotNull(firstname);
        checkNotNull(lastname);
        checkNotNull(permission);
        checkNotNull(password);
        checkNotNull(status);
        checkNotNull(email);
        checkNotNull(corporation);

        if (isEmailAlreadyTaken(email)){
            throw new IllegalArgumentException(String.format("[UserService] The email %s is already taken", email));
        }

        String passwordGenerated;
        try {
            passwordGenerated = passwordService.generateHash(password);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new IllegalArgumentException("[UserService] Error generating password for new user", e);
        }

        User user = new User(
                UUID.randomUUID(),
                firstname,
                lastname,
                email,
                String.format("%s.%s", firstname, lastname),
                passwordGenerated,
                status,
                permission,
                DateTime.now(),
                corporation
        );

        userRepository.save(user);

        return user;
    }

    public boolean isEmailAlreadyTaken(String email) {
        checkNotNull(email);

        return userRepository.findByEmail(email).isPresent();
    }
    
    @Transactional(readOnly = false)
    public void delete(UUID id) {
        userRepository.delete(id);
    }

    @Transactional(readOnly = false)
    public void delete(User user) {
        userRepository.delete(user.getId());
    }

    @Transactional(readOnly = true)
    public Optional<User> findById(UUID id) {
        return userRepository.findById(id);
    }
}
