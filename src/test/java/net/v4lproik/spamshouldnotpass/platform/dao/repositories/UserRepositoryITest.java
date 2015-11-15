package net.v4lproik.spamshouldnotpass.platform.dao.repositories;

import net.v4lproik.spamshouldnotpass.platform.client.postgres.DatabaseTestConfiguration;
import net.v4lproik.spamshouldnotpass.platform.client.postgres.SqlDatabaseInitializer;
import net.v4lproik.spamshouldnotpass.platform.models.MemberPermission;
import net.v4lproik.spamshouldnotpass.platform.models.MemberStatus;
import net.v4lproik.spamshouldnotpass.platform.models.entities.User;
import org.joda.time.DateTime;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.UUID;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
        classes = {
                DatabaseTestConfiguration.class,
                UserRepository.class,
        })
public class UserRepositoryITest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SqlDatabaseInitializer databaseInitializer;

    private final UUID uuid = UUID.randomUUID();
    private User user;

    @Before
    public void setUp(){
        try{
            databaseInitializer.createDatabase();
        }catch (Exception e){
            e.printStackTrace();
        }

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
    }

    @Test
    public void testSave() throws Exception {
        userRepository.save(
                user
        );

        assertEquals(userRepository.findById(user.getId()), user);
    }

    @After
    public void cleanUp() throws Exception {
        userRepository.delete(user.getId());
    }
}