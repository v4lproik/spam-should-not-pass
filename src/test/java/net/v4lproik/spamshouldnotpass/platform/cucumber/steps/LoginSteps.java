package net.v4lproik.spamshouldnotpass.platform.cucumber.steps;


import com.fasterxml.jackson.databind.ObjectMapper;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import net.v4lproik.spamshouldnotpass.platform.client.ClientTestConfiguration;
import net.v4lproik.spamshouldnotpass.platform.controllers.UserController;
import net.v4lproik.spamshouldnotpass.platform.models.BasicMember;
import net.v4lproik.spamshouldnotpass.platform.models.dto.toAuthUserDTO;
import net.v4lproik.spamshouldnotpass.platform.models.entities.User;
import net.v4lproik.spamshouldnotpass.platform.models.response.ApiKeyResponse;
import net.v4lproik.spamshouldnotpass.platform.models.response.PlatformResponse;
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
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Optional;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertFalse;
import static net.v4lproik.spamshouldnotpass.platform.cucumber.steps.CommonSteps.mvcResult;
import static net.v4lproik.spamshouldnotpass.platform.cucumber.steps.CommonSteps.user;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
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
    private CacheSessionRepository sessionRepo;

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
        mvcResult = mockMvc.perform(post("/user/auth")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(
                        new toAuthUserDTO(
                                user.email,
                                user.password
                        )
                ))
        ).andExpect(status().isOk()).andReturn();

        UserResponse response = objectMapper.readValue(mvcResult.getResponse().getContentAsString(),
                UserResponse.class);

        if (response.getStatus().equals(PlatformResponse.Status.OK)) {
            user.authToken = response.getToken();
            user.userId = response.getUser().getId();
            user.permission = response.getUser().getPermission();
            user.status = response.getUser().getStatus();
        }
    }

    @When("^He submits wrong credentials$")
    public void heSubmitsWrongCredentials() throws Throwable
    {
        mvcResult = mockMvc.perform(post("/user/auth")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(
                        new toAuthUserDTO(
                                user.email,
                                user.password + "wrong"
                        )
                ))
        ).andExpect(status().isOk()).andReturn();
    }

    @When("^He creates an API key$")
    public void heCreatesAnAPIKey() throws Throwable {
        mvcResult = mockMvc.perform(post("/user/create-api-key")
                .contentType(MediaType.APPLICATION_JSON)
                .requestAttr(CacheSessionRepository.MEMBER_KEY,
                        new BasicMember(user.userId,user.email,user.nickname,user.status,user.permission,user.corporation))
        ).andExpect(status().isOk()).andReturn();
    }

    @When("^He deletes his account$")
    public void heAccessUserInfo() throws Throwable {
        mvcResult = mockMvc.perform(post("/user/delete")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user.userId))
        ).andExpect(status().isOk()).andReturn();
    }

    @And("^He logged out$")
    public void heLoggedOut() throws Throwable {
        mvcResult = mockMvc.perform(post("/user/logout")
                .contentType(MediaType.APPLICATION_JSON)
                .header("x-auth-token", user.authToken)
        ).andExpect(status().isOk()).andReturn();
    }


    @Then("^He is logged in$")
    public void heIsLoggedIn() throws Throwable {

        final UserResponse response = objectMapper.readValue(mvcResult.getResponse().getContentAsString(),
                UserResponse.class);

        assertEquals(response.getStatus(), PlatformResponse.Status.OK);
        assertNotNull(response.getToken());
        assertEquals(response.getUser().getId(), user.userId);
        assertEquals(response.getUser().getEmail(), user.email);
        assertEquals(response.getUser().getStatus(), user.status);
        assertEquals(response.getUser().getPermission(), user.permission);
    }

    @Then("^He is not logged in$")
    public void heIsNotLoggedIn() throws Throwable {

        final UserResponse response = objectMapper.readValue(mvcResult.getResponse().getContentAsString(),
                UserResponse.class);

        assertEquals(response.getStatus(), PlatformResponse.Status.NOK);
    }

    @Then("^He is logged out")
    public void heIsLoggedOut() throws Throwable {

        UserResponse response = objectMapper.readValue(mvcResult.getResponse().getContentAsString(),
                UserResponse.class);

        assertEquals(response.getStatus(), PlatformResponse.Status.OK);
        assertNull(sessionRepo.getSession(user.authToken));
    }

    @Then("^He receives an error$")
    public void heReceivesAnError() throws Throwable {
        UserResponse response = objectMapper.readValue(mvcResult.getResponse().getContentAsString(),
                UserResponse.class);

        assertEquals(response.getStatus(), PlatformResponse.Status.NOK);
        assertEquals(response.getError(), PlatformResponse.Error.INVALID_INPUT);
        assertNull(response.getUser());
    }

    @Then("^The user is deleted$")
    public void theUserIsDeleted() throws Throwable {
        UserResponse response = objectMapper.readValue(mvcResult.getResponse().getContentAsString(),
                UserResponse.class);

        assertEquals(response.getStatus(), PlatformResponse.Status.OK);

        Optional<User> usr = userRepository.findById(user.userId);
        assertFalse(!usr.isPresent());
    }

    @Then("^He receives an API key$")
    public void heReceivesAnAPIKey() throws Throwable {
        mvcResult = mockMvc.perform(post("/user/get-api-key")
                .contentType(MediaType.APPLICATION_JSON)
                .requestAttr(CacheSessionRepository.MEMBER_KEY,
                        new BasicMember(user.userId,user.email,user.nickname,user.status,user.permission,user.corporation))
        ).andExpect(status().isOk()).andReturn();

        ApiKeyResponse response = objectMapper.readValue(mvcResult.getResponse().getContentAsString(),
                ApiKeyResponse.class);

        assertEquals(response.getStatus(), PlatformResponse.Status.OK);
        assertEquals(response.getKey().length(), 32);
    }
}
