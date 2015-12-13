package net.v4lproik.spamshouldnotpass.platform.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import net.v4lproik.spamshouldnotpass.platform.client.dynamodb.DynamoDBTablesInitializer;
import net.v4lproik.spamshouldnotpass.platform.client.dynamodb.DynamoDBTestConfiguration;
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

import static junit.framework.TestCase.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {SpringAppConfig.class, DatabaseTestConfiguration.class, DynamoDBTestConfiguration.class})
@WebAppConfiguration
public class ApiControllerITest {

    @Autowired
    SqlDatabaseInitializer databaseInitializer;

    @Autowired
    DynamoDBTablesInitializer dynamoDBTablesInitializer;

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

    @Autowired
    private AuthorInfoRepository authorInfoRepository;

    private MockMvc mockMvc;

    private User user;
    private UUID userId;
    private UUID ruleId;
    private UUID schemeId;
    private UUID schemeId2;
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

        try{
            dynamoDBTablesInitializer.createTables();
        }catch (Exception e){
            //
        }

        user = new User(
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
        );
    }

    @Test
    public void test_checkComment() throws Exception {

        userId = userRepository.save(user);

        schemeId = schemesRepository.save(
                new Scheme(
                        UUID.randomUUID(),
                        "{\"java.lang.String\":[{\"name\":\"documentId\",\"position\":1,\"locked\":true,\"provided\":false},{\"name\":\"object\",\"position\":2,\"locked\":true,\"provided\":false},{\"name\":\"content\",\"position\":3,\"locked\":true,\"provided\":false}]}",
                        user.getId(),
                        DateTime.now(),
                        DateTime.now(),
                        SchemeType.SPAM
                )
        );

        schemeId2 = schemesRepository.save(
                new Scheme(
                        UUID.randomUUID(),
                        "{\"java.lang.Boolean\":[{\"name\":\"isPremium\",\"position\":4,\"locked\":true,\"provided\":false}],\"java.lang.String\":[{\"name\":\"userId\",\"position\":1,\"locked\":true,\"provided\":false},{\"name\":\"email\",\"position\":2,\"locked\":true,\"provided\":false},{\"name\":\"firstname\",\"position\":3,\"locked\":true,\"provided\":false},{\"name\":\"lastname\",\"position\":3,\"locked\":true,\"provided\":false},{\"name\":\"NumberOfDocumentsSubmittedInTheLast5min\",\"position\":5,\"locked\":true,\"provided\":true}]}",
                        user.getId(),
                        DateTime.now(),
                        DateTime.now(),
                        SchemeType.SPAMMER
                )
        );

        Rule rule = new Rule(
                UUID.randomUUID(),
                "Bad Firstname",
                "{'super content news', 'manu', 'cedric'}.contains(content)",
                RuleType.DOCUMENT,
                user.getId(),
                DateTime.now(),
                DateTime.now()
        );

        ruleId = rulesRepository.save(rule);

        Context context = new Context(
                UUID.randomUUID(),
                "context-test",
                user.getId(),
                DateTime.now(),
                DateTime.now()
        );

        context.setRules(Lists.newArrayList(rule));
        contextId = contextRepository.save(context);


        toGetApiDTO toGet = new toGetApiDTO();
        toGet.setContext(contextId.toString());
        List<APIInformationDTO> list = Lists.newArrayList();
        list.add(new APIInformationDTO("documentId", UUID.randomUUID().toString()));
        list.add(new APIInformationDTO("userId", UUID.randomUUID().toString()));
        list.add(new APIInformationDTO("email", "fakeEmail@fakeEmail.fr"));
        list.add(new APIInformationDTO("content", "super content news"));
        list.add(new APIInformationDTO("object", "title"));
        toGet.setInformation(list);

        MvcResult result = mockMvc.perform(post("/api/v1/check")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(toGet))
                        .requestAttr(CacheSessionRepository.MEMBER_KEY, new BasicMember(userId, "email", "nickname", MemberStatus.ADMIN, MemberPermission.REGULAR, "corporation"))
        ).andExpect(status().isOk())
                .andReturn();

        SpamResponse response = objectMapper.readValue(result.getResponse().getContentAsString(), SpamResponse.class);

        assertEquals(response.getIsSpam().toString(), "true");
        assertEquals(response.getReason(), rule.getName());
    }


    @Test
    public void test_check3SameComments() throws Exception {
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
                        "{\"java.lang.String\":[{\"name\":\"documentId\",\"position\":1,\"locked\":true,\"provided\":false},{\"name\":\"object\",\"position\":2,\"locked\":true,\"provided\":false},{\"name\":\"content\",\"position\":3,\"locked\":true,\"provided\":false}]}",
                        userId,
                        DateTime.now(),
                        DateTime.now(),
                        SchemeType.SPAM
                )
        );

        schemeId2 = schemesRepository.save(
                new Scheme(
                        UUID.randomUUID(),
                        "{\"java.lang.Integer\":[{\"name\":\"numberOfDocumentsSubmittedInTheLast5min\",\"position\":5,\"locked\":true,\"provided\":true}],\"java.lang.Boolean\":[{\"name\":\"isPremium\",\"position\":4,\"locked\":true,\"provided\":false}],\"java.lang.String\":[{\"name\":\"userId\",\"position\":1,\"locked\":true,\"provided\":false},{\"name\":\"email\",\"position\":2,\"locked\":true,\"provided\":false},{\"name\":\"firstname\",\"position\":3,\"locked\":true,\"provided\":false},{\"name\":\"lastname\",\"position\":3,\"locked\":true,\"provided\":false}]}",
                        userId,
                        DateTime.now(),
                        DateTime.now(),
                        SchemeType.SPAMMER
                )
        );

        Rule rule = new Rule(
                UUID.randomUUID(),
                "Same message submitted 3 times within 5 minutes",
                "numberOfDocumentsSubmittedInTheLast5min >= 3",
                RuleType.DOCUMENT,
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
        list.add(new APIInformationDTO("userId", UUID.randomUUID().toString()));
        list.add(new APIInformationDTO("email", "fakeEmail@fakeEmail.fr"));
        list.add(new APIInformationDTO("content", "super content news"));
        list.add(new APIInformationDTO("object", "title"));
        toGet.setInformation(list);


        //1 msg submit
        MvcResult result = mockMvc.perform(post("/api/v1/check")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(toGet))
                        .requestAttr(CacheSessionRepository.MEMBER_KEY, new BasicMember(userId, "email", "nickname", MemberStatus.ADMIN, MemberPermission.REGULAR, "corporation"))
        ).andExpect(status().isOk())
                .andReturn();

        SpamResponse response = objectMapper.readValue(result.getResponse().getContentAsString(), SpamResponse.class);

        assertEquals(response.getIsSpam().toString(), "false");


        //2 msg submit
        result = mockMvc.perform(post("/api/v1/check")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(toGet))
                        .requestAttr(CacheSessionRepository.MEMBER_KEY, new BasicMember(userId, "email", "nickname", MemberStatus.ADMIN, MemberPermission.REGULAR, "corporation"))
        ).andExpect(status().isOk())
                .andReturn();

        response = objectMapper.readValue(result.getResponse().getContentAsString(), SpamResponse.class);

        assertEquals(response.getIsSpam().toString(), "false");


        //3 msg submit
        result = mockMvc.perform(post("/api/v1/check")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(toGet))
                        .requestAttr(CacheSessionRepository.MEMBER_KEY, new BasicMember(userId, "email", "nickname", MemberStatus.ADMIN, MemberPermission.REGULAR, "corporation"))
        ).andExpect(status().isOk())
                .andReturn();

        response = objectMapper.readValue(result.getResponse().getContentAsString(), SpamResponse.class);

        assertEquals(response.getIsSpam().toString(), "false");


        //4 msg submit
        result = mockMvc.perform(post("/api/v1/check")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(toGet))
                        .requestAttr(CacheSessionRepository.MEMBER_KEY, new BasicMember(userId, "email", "nickname", MemberStatus.ADMIN, MemberPermission.REGULAR, "corporation"))
        ).andExpect(status().isOk())
                .andReturn();

        response = objectMapper.readValue(result.getResponse().getContentAsString(), SpamResponse.class);

        assertEquals(response.getIsSpam().toString(), "true");
        assertEquals(response.getReason(), rule.getName());
    }


    @After
    public void cleanUp(){
        rulesRepository.delete(ruleId);
        schemesRepository.delete(schemeId);
        schemesRepository.delete(schemeId2);
        contextRepository.delete(contextId);
        userRepository.delete(userId);

        try{
            dynamoDBTablesInitializer.deleteTables();
        }catch (Exception e){
            //
        }
    }
}