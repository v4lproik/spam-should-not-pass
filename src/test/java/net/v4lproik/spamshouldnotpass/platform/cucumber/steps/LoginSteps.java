package net.v4lproik.spamshouldnotpass.platform.cucumber.steps;


import com.fasterxml.jackson.databind.ObjectMapper;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import net.v4lproik.spamshouldnotpass.platform.client.ClientTestConfiguration;
import net.v4lproik.spamshouldnotpass.platform.controllers.UserController;
import net.v4lproik.spamshouldnotpass.platform.models.BasicMember;
import net.v4lproik.spamshouldnotpass.platform.models.MemberPermission;
import net.v4lproik.spamshouldnotpass.platform.models.MemberStatus;
import net.v4lproik.spamshouldnotpass.platform.models.dto.toCreateUserDTO;
import net.v4lproik.spamshouldnotpass.platform.models.response.UserResponse;
import net.v4lproik.spamshouldnotpass.platform.repositories.CacheSessionRepository;
import net.v4lproik.spamshouldnotpass.platform.repositories.UserRepository;
import net.v4lproik.spamshouldnotpass.spring.SpringAppConfig;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static net.v4lproik.spamshouldnotpass.platform.cucumber.steps.CommonSteps.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ContextConfiguration(classes = {
        SpringAppConfig.class, ClientTestConfiguration.class
})
@WebAppConfiguration
public class LoginSteps
{
    @Autowired
    private UserController userController;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @When("^He submits its credentials$")
    public void heSubmitsItsCredentials() throws Throwable {

        MvcResult res = mockMvc.perform(post("/user/auth")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(
                        new toCreateUserDTO(
                                user.firstname,
                                user.lastname,
                                user.email,
                                user.password,
                                MemberStatus.USER.name(),
                                MemberPermission.REGULAR.name(),
                                user.corporation
                        )
                ))
        ).andExpect(status().isOk()).andReturn();

        UserResponse response = objectMapper.readValue(res.getResponse().getContentAsString(), UserResponse.class);
        user.userId = response.getUser().getId();
        user.authToken = response.getToken();
        user.permission = response.getUser().getPermission();
        user.status = response.getUser().getStatus();
    }

    @Then("^He is logged in$")
    public void heIsLoggedIn() throws Throwable {
        mockMvc.perform(post("/user/info")
                .contentType(MediaType.APPLICATION_JSON)
                .requestAttr(CacheSessionRepository.MEMBER_KEY, new BasicMember(user.userId,user.email,user.nickname,user.status,user.permission,user.corporation))
        ).andExpect(status().isOk()).andReturn();
    }

    @After
    public void cleanUp(){
        userRepository.delete(user.userId);
    }
}
