package net.v4lproik.spamshouldnotpass.platform.dao.repositories;

import net.v4lproik.spamshouldnotpass.platform.client.postgres.DatabaseTestConfiguration;
import net.v4lproik.spamshouldnotpass.platform.client.postgres.SqlDatabaseInitializer;
import net.v4lproik.spamshouldnotpass.platform.models.MemberPermission;
import net.v4lproik.spamshouldnotpass.platform.models.MemberStatus;
import net.v4lproik.spamshouldnotpass.platform.models.RuleType;
import net.v4lproik.spamshouldnotpass.platform.models.entities.Rule;
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
                RulesRepository.class,
        })
public class RulesRepositoryITest {

    @Autowired
    private RulesRepository rulesRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SqlDatabaseInitializer databaseInitializer;

    UUID userUuid;
    UUID ruleUuid;

    @Before
    public void setUp(){
        try{
            databaseInitializer.createDatabase();
        }catch (Exception e){
        }

        userUuid = UUID.randomUUID();
        ruleUuid = UUID.randomUUID();
    }

    @Test
    public void testSave() throws Exception {

        User user = new User(
                userUuid,
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

        userRepository.save(
                user
        );

        UUID uuid = rulesRepository.save(
                new Rule(
                        ruleUuid,
                        "new rule",
                        "firsname.equals('spidercochon')",
                        RuleType.USER,
                        user.getId(),
                        DateTime.now(),
                        DateTime.now()
                )
        );

        Rule rule = rulesRepository.findById(uuid);

        assertEquals(ruleUuid, uuid);
        assertEquals(rule.getId(), uuid);
        assertEquals(rule.getUserId(), user.getId());
    }

    @After
    public void cleanUp() throws Exception {
        rulesRepository.delete(ruleUuid);
        userRepository.delete(userUuid);
    }
}