package net.v4lproik.spamshouldnotpass.platform.controllers;

import junit.framework.TestCase;
import net.v4lproik.spamshouldnotpass.platform.dao.repositories.CacheSessionRepository;
import net.v4lproik.spamshouldnotpass.platform.dao.repositories.SchemesRepository;
import net.v4lproik.spamshouldnotpass.platform.dao.repositories.UserRepository;
import net.v4lproik.spamshouldnotpass.platform.models.BasicMember;
import net.v4lproik.spamshouldnotpass.platform.models.MemberPermission;
import net.v4lproik.spamshouldnotpass.platform.models.MemberStatus;
import net.v4lproik.spamshouldnotpass.platform.service.api.entities.User;
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
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {SpringAppConfig.class})
@WebAppConfiguration
public class SpammerControllerITest extends TestCase {

    @Autowired
    private SchemesRepository schemesRepository;

    @Autowired
    private SpammerController spammerController;

    @Autowired
    private UserRepository userRepository;

    private MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(spammerController).build();
    }

    @Test
    public void testCreateSpammerDocument() throws Exception {

        UUID uuid = userRepository.save(new User(
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

        mockMvc.perform(post("/spammer/create-spammer-document")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"String\":\"test_\"}")
                        .requestAttr(CacheSessionRepository.MEMBER_KEY, new BasicMember(uuid))
        ).andExpect(status().isOk());

        assertEquals(schemesRepository.listByUserId(uuid).size(), 1);
    }


}