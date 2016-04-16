package net.v4lproik.spamshouldnotpass.platform.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.v4lproik.spamshouldnotpass.platform.client.postgres.DatabaseTestConfiguration;
import net.v4lproik.spamshouldnotpass.platform.client.postgres.SqlDatabaseInitializer;
import net.v4lproik.spamshouldnotpass.platform.models.MemberPermission;
import net.v4lproik.spamshouldnotpass.platform.models.MemberStatus;
import net.v4lproik.spamshouldnotpass.platform.models.dto.toCreateUserDTO;
import net.v4lproik.spamshouldnotpass.platform.repositories.UserRepository;
import net.v4lproik.spamshouldnotpass.platform.services.PasswordService;
import net.v4lproik.spamshouldnotpass.platform.services.UserService;
import net.v4lproik.spamshouldnotpass.spring.SpringAppConfig;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.session.SessionRepository;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import javax.servlet.http.HttpServletRequest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {SpringAppConfig.class, DatabaseTestConfiguration.class})
@WebAppConfiguration
public class UserControllerITest {

    @Autowired
    private SqlDatabaseInitializer databaseInitializer;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Mock
    private PasswordService passwordService;

    @Mock
    private UserService userService;

    @Mock
    private HttpServletRequest req;

    @Mock
    private SessionRepository sessionRepo;

    @InjectMocks
    private UserController userController;

    private MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    public void testCreateUser_withData_shouldReturnMember() throws Exception {
        // Given
        final String firstname = "firstname";
        final String lastname = "lastname";
        final String login = "spidercochon@superspidercochon.com";
        final String password = "spider";
        final String permission = "REGULAR";
        final String status = "USER";
        final String corporation = "google";

        MvcResult res = mockMvc.perform(post("/user/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new toCreateUserDTO(firstname, lastname, login, password, MemberStatus.USER.toString(), MemberPermission.REGULAR.toString(), corporation)))
        )
                .andExpect(status().isOk()).andReturn();
    }
}