package net.v4lproik.spamshouldnotpass.platform.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import net.v4lproik.spamshouldnotpass.platform.client.postgres.DatabaseTestConfiguration;
import net.v4lproik.spamshouldnotpass.platform.client.postgres.SqlDatabaseInitializer;
import net.v4lproik.spamshouldnotpass.platform.dao.repositories.*;
import net.v4lproik.spamshouldnotpass.platform.models.*;
import net.v4lproik.spamshouldnotpass.platform.models.dto.APIInformationDTO;
import net.v4lproik.spamshouldnotpass.platform.models.dto.toGetApiDTO;
import net.v4lproik.spamshouldnotpass.platform.models.entities.Context;
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

import java.util.List;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {SpringAppConfig.class, DatabaseTestConfiguration.class})
@WebAppConfiguration
public class ApiControllerITest {

    @Autowired
    SqlDatabaseInitializer databaseInitializer;

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

    @Autowired
    private ContextRepository contextRepository;

    private MockMvc mockMvc;

    private UUID userId;
    private UUID ruleId;
    private UUID schemeId;
    private UUID contextId;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(apiController).build();

        try{
            databaseInitializer.createDatabase();
        }catch (Exception e){
            //
        }
    }

    @Test
    public void test_checkComment() throws Exception {
        userId = userRepository.save(
                new User(
                        UUID.randomUUID(),
                        "firstname",
                        "lastname",
                        "email",
                        "nickname",
                        "password",
                        MemberStatus.ADMIN,
                        MemberPermission.REGULAR,
                        DateTime.now(),
                        "corporation"
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

        Rule rule = new Rule(
                UUID.randomUUID(),
                "Bad Firstname",
                "{'super content news', 'manu', 'cedric'}.contains(content)",
                RuleType.SPAM,
                userId,
                DateTime.now(),
                DateTime.now()
        );

        ruleId = rulesRepository.save(
                rule
        );

        Context context = new Context(
                UUID.randomUUID(),
                "context-test",
                userId,
                DateTime.now(),
                DateTime.now()
        );

        context.setRules(Lists.newArrayList(rule));
        contextId = contextRepository.save(context);


        toGetApiDTO toGet = new toGetApiDTO();
        toGet.setContext(contextId.toString());
        List<APIInformationDTO> list = Lists.newArrayList();
        list.add(new APIInformationDTO("documentId", UUID.randomUUID().toString()));
        list.add(new APIInformationDTO("content", "super content news"));
        list.add(new APIInformationDTO("object", "title"));
        toGet.setInformation(list);

        MvcResult result = mockMvc.perform(post("/api/v1/check")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(toGet))
                        .requestAttr(CacheSessionRepository.MEMBER_KEY, new BasicMember(userId))
        ).andExpect(status().isOk())
                .andReturn();

        System.out.println(objectMapper.readValue(result.getResponse().getContentAsString(), SpamResponse.class).toString());
    }

    @After
    public void cleanUp(){
        rulesRepository.delete(ruleId);
        schemesRepository.delete(schemeId);
        contextRepository.delete(contextId);
        userRepository.delete(userId);
    }
}