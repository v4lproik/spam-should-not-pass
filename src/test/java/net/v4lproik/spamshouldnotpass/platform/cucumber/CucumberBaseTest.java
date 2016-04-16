package net.v4lproik.spamshouldnotpass.platform.cucumber;


import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        format = { "pretty", "html:target/cucumber" },
        glue = { "net.v4lproik.spamshouldnotpass.platform.cucumber.steps" },
        features = { "src/test/resources/net/v4lproik/spamshouldnotpass/platform/cucumber/features" }
)
public class CucumberBaseTest
{
}
