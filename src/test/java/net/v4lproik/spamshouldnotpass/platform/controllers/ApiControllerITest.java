package net.v4lproik.spamshouldnotpass.platform.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import net.v4lproik.spamshouldnotpass.platform.dao.repositories.CacheSessionRepository;
import net.v4lproik.spamshouldnotpass.platform.dao.repositories.RulesRepository;
import net.v4lproik.spamshouldnotpass.platform.dao.repositories.SchemesRepository;
import net.v4lproik.spamshouldnotpass.platform.dao.repositories.UserRepository;
import net.v4lproik.spamshouldnotpass.platform.models.*;
import net.v4lproik.spamshouldnotpass.platform.models.entities.Rule;
import net.v4lproik.spamshouldnotpass.platform.models.entities.Scheme;
import net.v4lproik.spamshouldnotpass.platform.models.entities.User;
import net.v4lproik.spamshouldnotpass.platform.models.response.SpamResponse;
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
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Map;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {SpringAppConfig.class})
@WebAppConfiguration
public class ApiControllerITest {

    @Autowired
    private ApiController apiController;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private RulesRepository rulesRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SchemesRepository schemesRepository;

    private MockMvc mockMvc;

    private UUID userId;
    private UUID ruleId;
    private UUID schemeId;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(apiController).build();
    }

    @Test
    public void test_checkComment() throws Exception {
        userId = userRepository.save(new User(
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

        schemeId = schemesRepository.save(
                new Scheme(
                        UUID.randomUUID(),
                        "{\"java.lang.String\":[\"documentId\", \"content\", \"firstname\", \"lastname\"]}",
                        userId,
                        DateTime.now(),
                        DateTime.now(),
                        SchemeType.SPAM
                )
        );

        ruleId = rulesRepository.save(
                new Rule(
                        UUID.randomUUID(),
                        "Bad Firstname",
                        "{'joel', 'manu', 'cedric'}.contains(firstname)",
                        RuleType.SPAM,
                        userId,
                        DateTime.now(),
                        DateTime.now()
                )
        );

        Map<String, String> variables = Maps.newHashMap(
                ImmutableMap.of("documentId", UUID.randomUUID().toString(),
                        "content", "Message' content",
                        "firstname", "joel")
        );

        MvcResult result = mockMvc.perform(post("/api/v1/check-comment")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(variables))
                        .requestAttr(CacheSessionRepository.MEMBER_KEY, new BasicMember(userId))
        ).andExpect(status().isOk())
                .andReturn();

        System.out.println(objectMapper.readValue(result.getResponse().getContentAsString(), SpamResponse.class));
    }

    @After
    public void cleanUp(){
        rulesRepository.delete(ruleId);
        schemesRepository.delete(schemeId);
        userRepository.delete(userId);
    }
}