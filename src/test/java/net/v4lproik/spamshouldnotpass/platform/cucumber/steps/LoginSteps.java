package net.v4lproik.spamshouldnotpass.platform.cucumber.steps;


import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import net.v4lproik.spamshouldnotpass.platform.client.postgres.DatabaseTestConfiguration;
import net.v4lproik.spamshouldnotpass.platform.models.entities.User;
import net.v4lproik.spamshouldnotpass.platform.services.UserService;
import net.v4lproik.spamshouldnotpass.spring.SpringAppConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;

import static org.junit.Assert.assertEquals;

@ContextConfiguration(classes = {SpringAppConfig.class})
@WebAppConfiguration
public class LoginSteps
{
    @Autowired
    private UserService userService;

    private String login;
    private String password;
    private User user;

    @Given("^my login is \"([^\"]*)\"$")
    public void my_login_is(String login) throws Throwable {
        this.login = login;
    }

    @Given("^my password is \"([^\"]*)\"$")
    public void my_password_is(String password) throws Throwable {
        this.password = password;
    }

    @When("^I provide my credentials$")
    public void I_provide_my_credentials()
    {
        this.user = userService.authenticate(this.login, this.password);
    }

    @Then("^I should receive the following error \"([^\"]*)\"$")
    public void i_should_receive_the_following_error_status(String responseUser) throws Throwable {
        assertEquals(responseUser, this.user == null ? "null" : this.user.toString());
    }
}
