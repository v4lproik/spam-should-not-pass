package net.v4lproik.spamshouldnotpass.platform.service;

import junit.framework.TestCase;
import net.v4lproik.spamshouldnotpass.platform.repositories.UserRepository;
import net.v4lproik.spamshouldnotpass.platform.models.MemberPermission;
import net.v4lproik.spamshouldnotpass.platform.models.MemberStatus;
import net.v4lproik.spamshouldnotpass.platform.models.entities.User;
import net.v4lproik.spamshouldnotpass.platform.services.PasswordService;
import net.v4lproik.spamshouldnotpass.platform.services.UserService;
import org.joda.time.DateTime;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Optional;
import java.util.UUID;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceUTest extends TestCase {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordService passwordService;

    @InjectMocks
    UserService userService;

    private final UUID uuid = UUID.randomUUID();
    private User user;

    @Test
    public void test_save() throws Exception{
        user = new User(
                uuid,
                "firstname",
                "lastname",
                "email",
                "nickname",
                "password",
                MemberStatus.ADMIN,
                MemberPermission.REGULAR,
                DateTime.now(),
                "corporation"
        );

        User userExpected = new User(
                uuid,
                "firstname",
                "lastname",
                "email",
                "nickname",
                "encryptedPassword",
                MemberStatus.ADMIN,
                MemberPermission.REGULAR,
                DateTime.now(),
                "corporation"
        );

        when(passwordService.generateHash(user.getPassword())).thenReturn("encryptedPassword");
        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.empty());
        when(userRepository.save(userExpected)).thenReturn(Optional.of(userExpected.getId()));

        userService.save(
                user.getFirstname(),
                user.getLastname(),
                user.getStatus(),
                user.getPermission(),
                user.getEmail(),
                user.getPassword(),
                user.getCorporation()
        );

        verify(userRepository, atLeast(1)).save(any(User.class));
    }
}