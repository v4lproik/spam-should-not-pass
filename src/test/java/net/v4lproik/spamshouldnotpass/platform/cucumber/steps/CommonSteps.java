package net.v4lproik.spamshouldnotpass.platform.cucumber.steps;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Iterables;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import net.v4lproik.spamshouldnotpass.platform.client.ClientTestConfiguration;
import net.v4lproik.spamshouldnotpass.platform.client.postgres.SqlDatabaseInitializer;
import net.v4lproik.spamshouldnotpass.platform.cucumber.fixture.UserFixture;
import net.v4lproik.spamshouldnotpass.platform.models.MemberPermission;
import net.v4lproik.spamshouldnotpass.platform.models.MemberStatus;
import net.v4lproik.spamshouldnotpass.platform.models.dto.toCreateUserDTO;
import net.v4lproik.spamshouldnotpass.platform.models.response.UserResponse;
import net.v4lproik.spamshouldnotpass.platform.repositories.UserRepository;
import net.v4lproik.spamshouldnotpass.spring.SpringAppConfig;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@WebAppConfiguration
@ContextConfiguration(classes = {
        SpringAppConfig.class, ClientTestConfiguration.class
})
public class CommonSteps
{
    @Autowired
    private SqlDatabaseInitializer databaseInitializer;

    @Autowired
    private WebApplicationContext wac;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ObjectMapper objectMapper;

    public static UserFixture user;

    public static MvcResult mvcResult;

    private MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = webAppContextSetup(this.wac).build();
    }

    @Before(value = "@database")
    public void initDatabase(){
        System.out.println("Before1");

        try{
            databaseInitializer.createDatabase();
        }catch (Exception e){
        }
    }

    @Given("^A new user$")
    public void aNewUser(List<UserFixture> theMe) throws Throwable
    {
        user = Iterables.getOnlyElement(theMe);

        mvcResult = mockMvc.perform(post("/user/create")
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

        UserResponse response = objectMapper.readValue(mvcResult.getResponse().getContentAsString(),
                                                       UserResponse.class);

        if (response.getUser() != null)
        {
            user.userId = response.getUser().getId();
            user.permission = response.getUser().getPermission();
            user.status = response.getUser().getStatus();
        }
    }

    @After
    public void cleanUp(){
        userRepository.delete(user.userId);
    }
}
