package net.v4lproik.spamshouldnotpass.platform.controllers;

import net.v4lproik.spamshouldnotpass.platform.service.PasswordService;
import net.v4lproik.spamshouldnotpass.platform.service.UserService;
import net.v4lproik.spamshouldnotpass.spring.SpringAppConfig;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.session.SessionRepository;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import javax.servlet.http.HttpServletRequest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {SpringAppConfig.class})
@WebAppConfiguration
public class UserControllerUTest {

    @Mock
    PasswordService passwordService;

    @Mock
    UserService userService;

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

        mockMvc.perform(post("/user/create")
                        .param("firstname", firstname)
                        .param("lastname", lastname)
                        .param("status", status)
                        .param("permission", permission)
                        .param("email", login)
                        .param("password", password)
        )
                .andExpect(status().isOk());
    }
}