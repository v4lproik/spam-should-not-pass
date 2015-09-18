package net.v4lproik.googlanime.mvc.controllers;

import net.v4lproik.googlanime.service.api.PasswordService;
import net.v4lproik.googlanime.service.api.UserService;
import net.v4lproik.googlanime.spring.AppConfig;
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
@ContextConfiguration(classes = {AppConfig.class})
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
    public void testAuthUser_withData_shouldReturnAuthToken() throws Exception {
        // Given
        final String login = "spidercochon@superspidercochon.com";
        final String password = "spider";

        mockMvc.perform(post("/user/auth")
                        .param("login", login)
                        .param("password", password)
        )
                .andExpect(status().isOk());
    }

    @Test
    public void testCreateUser_withData_shouldReturnMember() throws Exception {
        // Given
        final String login = "spidercochon@superspidercochon.com";
        final String password = "spider";

        mockMvc.perform(post("/user/create")
                        .param("login", login)
                        .param("password", password)
        )
                .andExpect(status().isOk());
    }
}