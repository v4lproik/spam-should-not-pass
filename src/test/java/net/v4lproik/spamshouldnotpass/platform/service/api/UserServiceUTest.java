package net.v4lproik.spamshouldnotpass.platform.service.api;

import junit.framework.TestCase;
import net.v4lproik.spamshouldnotpass.platform.dao.api.UserDao;
import net.v4lproik.spamshouldnotpass.platform.models.MemberPermission;
import net.v4lproik.spamshouldnotpass.platform.models.MemberStatus;
import net.v4lproik.spamshouldnotpass.platform.service.api.entities.User;
import org.joda.time.DateTime;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.UUID;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceUTest extends TestCase {

    @Mock
    private UserDao userDao;

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
                DateTime.now()
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
                DateTime.now()
        );

        when(passwordService.generateHash(user.getPassword())).thenReturn("encryptedPassword");
        when(userDao.save(userExpected)).thenReturn(userExpected.getId());

        User generated = userService.save(
                user.getFirstname(),
                user.getLastname(),
                user.getStatus().toString(),
                user.getPermission().toString(),
                user.getEmail(),
                user.getPassword()
        );

        verify(userDao, atLeast(1)).save(any(User.class));
    }
}