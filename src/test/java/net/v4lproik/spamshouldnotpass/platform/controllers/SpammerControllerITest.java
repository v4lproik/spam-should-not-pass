package net.v4lproik.spamshouldnotpass.platform.controllers;

import junit.framework.TestCase;
import net.v4lproik.spamshouldnotpass.platform.dao.repositories.CacheSessionRepository;
import net.v4lproik.spamshouldnotpass.platform.dao.repositories.SchemesRepository;
import net.v4lproik.spamshouldnotpass.platform.models.BasicMember;
import net.v4lproik.spamshouldnotpass.spring.SpringAppConfig;
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

    private MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(spammerController).build();
    }

    @Test
    public void testCreateSpammerDocument() throws Exception {
        UUID userId = UUID.fromString("8416d61c-247f-4334-80cc-2d03d0defe31");

        mockMvc.perform(post("/spammer/create-spammer-document")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"String\":\"test_\"}")
                .requestAttr(CacheSessionRepository.MEMBER_KEY, new BasicMember(userId))
        ).andExpect(status().isOk());

        assertEquals(schemesRepository.listByUserId(userId).size(), 1);
    }
}