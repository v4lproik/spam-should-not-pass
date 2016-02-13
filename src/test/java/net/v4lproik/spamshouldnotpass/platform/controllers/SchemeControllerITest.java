package net.v4lproik.spamshouldnotpass.platform.controllers;

import net.v4lproik.spamshouldnotpass.platform.client.postgres.DatabaseTestConfiguration;
import net.v4lproik.spamshouldnotpass.platform.client.postgres.SqlDatabaseInitializer;
import net.v4lproik.spamshouldnotpass.platform.dao.repositories.CacheSessionRepository;
import net.v4lproik.spamshouldnotpass.platform.dao.repositories.SchemesRepository;
import net.v4lproik.spamshouldnotpass.platform.dao.repositories.UserRepository;
import net.v4lproik.spamshouldnotpass.platform.models.BasicMember;
import net.v4lproik.spamshouldnotpass.platform.models.MemberPermission;
import net.v4lproik.spamshouldnotpass.platform.models.MemberStatus;
import net.v4lproik.spamshouldnotpass.platform.models.entities.User;
import net.v4lproik.spamshouldnotpass.spring.SpringAppConfig;
import org.joda.time.DateTime;
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

import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {SpringAppConfig.class, DatabaseTestConfiguration.class,
})
@WebAppConfiguration
public class SchemeControllerITest {

    @Autowired
    SqlDatabaseInitializer databaseInitializer;

    @Autowired
    private SchemesRepository schemesRepository;

    @Autowired
    private SchemeController schemeController;

    @Autowired
    private UserRepository userRepository;

    private MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(schemeController).build();

        try{
            databaseInitializer.createDatabase();
        }catch (Exception e){
            //
        }
    }

    @Test
    public void testCreateSpammerDocument() throws Exception {

//        UUID uuid = userRepository.save(new User(
//                        UUID.randomUUID(),
//                        "firstname",
//                        "lastname",
//                        "email",
//                        "nickname",
//                        "password",
//                        MemberStatus.ADMIN,
//                        MemberPermission.REGULAR,
//                        DateTime.now(),
//                        "corporation"
//                )
//        );
//
//        mockMvc.perform(post("/scheme/create/user")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content("{\"properties\": [{\"variableType\": \"java.lang.String\", \"variableName\": \"test\", \"position\": \"0\", \"visibility\": \"true\" }]}")
//                        .requestAttr(CacheSessionRepository.MEMBER_KEY, new BasicMember(uuid, "email", "nickname", MemberStatus.ADMIN, MemberPermission.REGULAR, "corporation"))
//        ).andExpect(status().isOk());
//
//        MvcResult result = mockMvc.perform(post("/scheme/get/user")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content("{\"id\":\"2823ef37-7265-459d-8df4-8a66729ecf19\"}")
//                .requestAttr(CacheSessionRepository.MEMBER_KEY, new BasicMember(uuid, "email", "nickname", MemberStatus.ADMIN, MemberPermission.REGULAR, "corporation")))
//                .andExpect(status().isOk())
//                .andReturn();
//
//        assertEquals(schemesRepository.listByUserId(uuid).size(), 1);
    }
}