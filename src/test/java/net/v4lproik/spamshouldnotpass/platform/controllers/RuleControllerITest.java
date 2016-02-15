package net.v4lproik.spamshouldnotpass.platform.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.v4lproik.spamshouldnotpass.platform.client.postgres.DatabaseTestConfiguration;
import net.v4lproik.spamshouldnotpass.platform.client.postgres.SqlDatabaseInitializer;
import net.v4lproik.spamshouldnotpass.platform.dao.repositories.CacheSessionRepository;
import net.v4lproik.spamshouldnotpass.platform.dao.repositories.RulesRepository;
import net.v4lproik.spamshouldnotpass.platform.dao.repositories.UserRepository;
import net.v4lproik.spamshouldnotpass.platform.models.BasicMember;
import net.v4lproik.spamshouldnotpass.platform.models.RuleType;
import net.v4lproik.spamshouldnotpass.platform.models.dto.toCreateRuleDTO;
import net.v4lproik.spamshouldnotpass.platform.models.dto.toCreateUserDTO;
import net.v4lproik.spamshouldnotpass.platform.models.dto.toUpdateRuleDTO;
import net.v4lproik.spamshouldnotpass.platform.models.entities.Rule;
import net.v4lproik.spamshouldnotpass.platform.models.entities.User;
import net.v4lproik.spamshouldnotpass.spring.SpringAppConfig;
import org.joda.time.DateTime;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {
        SpringAppConfig.class, DatabaseTestConfiguration.class,
})
@WebAppConfiguration
public class RuleControllerITest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    SqlDatabaseInitializer databaseInitializer;

    @Autowired
    private UserController userController;

    @Autowired
    private RuleController ruleController;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RulesRepository rulesRepository;

    private MockMvc mockUserMvc;
    private MockMvc mockRuleMvc;

    private User user;
    private Rule rule;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        mockUserMvc = MockMvcBuilders.standaloneSetup(userController).build();
        mockRuleMvc = MockMvcBuilders.standaloneSetup(ruleController).build();

        try{
            databaseInitializer.createDatabase();
        }catch (Exception e){
            //
        }
    }

    //TODO: Create test utility avoiding creating user
    @Test
    public void test_create_new_rule() throws Exception {

        // Given
        final String firstname = "firstname";
        final String lastname = "lastname";
        final String login = "spidercochon@superspidercochon.com";
        final String password = "spider";
        final String permission = "REGULAR";
        final String status = "USER";
        final String corporation = "google";

        mockUserMvc.perform(post("/user/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(
                        new toCreateUserDTO(firstname, lastname, login, password, status, permission, corporation))
                )
        )
                .andExpect(status().isOk()).andReturn();

        user = userRepository.findByEmail(login);
        final BasicMember member = new BasicMember(
                user.getId(),
                user.getEmail(),
                user.getNickname(),
                user.getStatus(),
                user.getPermission(),
                user.getCorporation()
        );

        rule = new Rule(
                UUID.randomUUID(),
                "test rule",
                "1 == 1",
                RuleType.DOCUMENT,
                member.getId(),
                DateTime.now(),
                DateTime.now()
        );
        mockRuleMvc.perform(post("/rule/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(
                        new toCreateRuleDTO(rule.getId(), rule.getName(), rule.getRule(), rule.getType())
                        )
                )
                .requestAttr(CacheSessionRepository.MEMBER_KEY, member)

        )
                .andExpect(status().isOk())
                .andReturn();

        final Rule ruleExpected = rulesRepository.findById(rule.getId());

        assertEquals(ruleExpected, rule);
    }

    @Test
    public void test_update_new_rule() throws Exception {

        // Given
        final String firstname = "firstname";
        final String lastname = "lastname";
        final String login = "spidercochon@superspidercochon.com";
        final String password = "spider";
        final String permission = "REGULAR";
        final String status = "USER";
        final String corporation = "google";

        mockUserMvc.perform(post("/user/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(
                        new toCreateUserDTO(firstname, lastname, login, password, status, permission, corporation))
                )
        )
                .andExpect(status().isOk()).andReturn();

        user = userRepository.findByEmail(login);
        final BasicMember member = new BasicMember(
                user.getId(),
                user.getEmail(),
                user.getNickname(),
                user.getStatus(),
                user.getPermission(),
                user.getCorporation()
        );
        rule = new Rule(
                UUID.randomUUID(),
                "test rule",
                "1 == 1",
                RuleType.DOCUMENT,
                member.getId(),
                DateTime.now(),
                DateTime.now()
        );

        mockRuleMvc.perform(post("/rule/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(
                        new toCreateRuleDTO(rule.getId(), rule.getName(), rule.getRule(), rule.getType())
                        )
                )
                .requestAttr(CacheSessionRepository.MEMBER_KEY, member)

        )
                .andExpect(status().isOk())
                .andReturn();


        mockRuleMvc.perform(
                post("/rule/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new toUpdateRuleDTO(rule.getId(),rule.getName(), rule.getName(), rule.getType())))
                        .requestAttr(CacheSessionRepository.MEMBER_KEY, member)

        )
                .andExpect(status().isOk())
                .andReturn();

        final Rule ruleUpdated = rulesRepository.findById(rule.getId());

        assertEquals(ruleUpdated, rule);
    }

    @After
    public void cleanUp(){
        rulesRepository.delete(rule.getId());
        userRepository.delete(user.getId());
    }
}