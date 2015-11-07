package net.v4lproik.spamshouldnotpass.platform.dao.repositories;

import net.v4lproik.spamshouldnotpass.platform.client.postgres.DatabaseTestConfiguration;
import net.v4lproik.spamshouldnotpass.platform.models.MemberPermission;
import net.v4lproik.spamshouldnotpass.platform.models.MemberStatus;
import net.v4lproik.spamshouldnotpass.platform.service.api.entities.User;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.UUID;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
        classes = {
                DatabaseTestConfiguration.class,
                UserRepository.class,
        })
public class UserRepositoryITest {

    @Autowired
    UserRepository userRepository;

    @Before
    public void setUp(){
    }

    @Test
    public void testSave() throws Exception {
        userRepository.save(
                new User(
                        UUID.randomUUID(),
                        "firstname",
                        "lastname",
                        "email",
                        "nickname",
                        "password",
                        MemberStatus.ADMIN,
                        MemberPermission.REGULAR,
                        DateTime.now()
                )
        );
    }

    @Test
    public void testDelete() throws Exception {

    }
}