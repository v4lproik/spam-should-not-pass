package net.v4lproik.spamshouldnotpass.platform.client.postgres;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
        classes = {
                DatabaseTestConfiguration.class,
        })
@WebAppConfiguration
public class ConfigPostgresITest {

    @Autowired
    Config config;

    @Test
    public void test_ping_postgres(){
        config.sessionFactoryConfig().getStatistics();
    }

}