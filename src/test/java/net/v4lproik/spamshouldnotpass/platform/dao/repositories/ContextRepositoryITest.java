package net.v4lproik.spamshouldnotpass.platform.dao.repositories;

import com.google.common.collect.Lists;
import net.v4lproik.spamshouldnotpass.platform.client.postgres.DatabaseTestConfiguration;
import net.v4lproik.spamshouldnotpass.platform.client.postgres.SqlDatabaseInitializer;
import net.v4lproik.spamshouldnotpass.platform.models.MemberPermission;
import net.v4lproik.spamshouldnotpass.platform.models.MemberStatus;
import net.v4lproik.spamshouldnotpass.platform.models.RuleType;
import net.v4lproik.spamshouldnotpass.platform.models.entities.Context;
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

import java.util.List;
import java.util.UUID;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
        classes = {
                DatabaseTestConfiguration.class,
                ContextRepository.class,
                RulesRepository.class,
                UserRepository.class
        })
public class ContextRepositoryITest {

    @Autowired
    private ContextRepository contextRepository;

    @Autowired
    private RulesRepository rulesRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SqlDatabaseInitializer databaseInitializer;

    private final UUID contextId = UUID.randomUUID();
    private final UUID userId = UUID.randomUUID();
    private final UUID rule1Id = UUID.randomUUID();
    private final UUID rule2Id = UUID.randomUUID();
    private Context context;
    private User user;

    @Before
    public void setUp(){
        try{
            databaseInitializer.createDatabase();
        }catch (Exception e){
//            e.printStackTrace();
        }

        user = new User(
                userId,
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
        userRepository.save(user);
    }

    @Test
    public void testSave() throws Exception {
        // Given
        context = new Context(
                contextId,
                "context-test",
                userId,
                DateTime.now(),
                DateTime.now()
        );

        //When
        contextRepository.save(context);


        //then
        assertEquals(contextRepository.findById(context.getId()), context);
    }

    @Test
    public void testSave_withRules() throws Exception {
        // Given
        context = new Context(
                contextId,
                "context-test",
                userId,
                DateTime.now(),
                DateTime.now()
        );

        final Rule rule1 = new Rule(
                rule1Id,
                "new rule arf !!!!!!!!!!!!!!",
                "firsname.equals('spidercochon')",
                RuleType.USER,
                user.getId(),
                DateTime.now(),
                DateTime.now()
        );

        //When
        rulesRepository.save(rule1);

        context.setRules(Lists.newArrayList(rule1));
        contextRepository.save(context);

        //then
        Context toGet = contextRepository.findByIdWithRules(context.getId());
        assertEquals(toGet, context);
        assertEquals(toGet.getRules().size(), 1);
        assertEquals(toGet.getRules().contains(rule1), true);
    }

    @Test
    public void testUpdate_withRules() throws Exception {
        // Given
        context = new Context(
                contextId,
                "context-test",
                userId,
                DateTime.now(),
                DateTime.now()
        );

        final Rule rule1 = new Rule(
                rule1Id,
                "new rule arf !!!!!!!!!!!!!!",
                "firsname.equals('spidercochon')",
                RuleType.USER,
                user.getId(),
                DateTime.now(),
                DateTime.now()
        );

        //When
        rulesRepository.save(rule1);

        context.setRules(Lists.newArrayList(rule1));
        contextRepository.save(context);

        // update
        final Rule rule2 = new Rule(
                rule2Id,
                "new rule 2 arf ???????????????????????????????",
                "firsname.equals('spidercochon')",
                RuleType.USER,
                user.getId(),
                DateTime.now(),
                DateTime.now()
        );
        rulesRepository.save(rule2);
        context.setRules(Lists.newArrayList(rule1, rule2));
        contextRepository.update(context);

        //then
        Context toGet = contextRepository.findByIdWithRules(context.getId());
        List<Rule> toGetRules = toGet.getRules();

        assertEquals(toGet, context);
        assertEquals(toGetRules.size(), 2);
        assertEquals(toGetRules.get(0).getId(), rule1.getId());
        assertEquals(toGetRules.get(1).getId(), rule2.getId());
    }

    @Test
    public void testdelete_withRules() throws Exception {
        // Given
        context = new Context(
                contextId,
                "context-test",
                userId,
                DateTime.now(),
                DateTime.now()
        );

        final Rule rule1 = new Rule(
                rule1Id,
                "new rule arf !!!!!!!!!!!!!!",
                "firsname.equals('spidercochon')",
                RuleType.USER,
                user.getId(),
                DateTime.now(),
                DateTime.now()
        );

        //When
        rulesRepository.save(rule1);

        context.setRules(Lists.newArrayList(rule1));
        contextRepository.save(context);


        contextRepository.delete(context.getId());

        //then
        Context toGet = contextRepository.findByIdWithRules(context.getId());

        assertEquals(toGet, null);
    }


    @After
    public void cleanUp() throws Exception {
        contextRepository.delete(contextId);

        rulesRepository.delete(rule1Id);
        rulesRepository.delete(rule2Id);

        userRepository.delete(userId);
    }
}