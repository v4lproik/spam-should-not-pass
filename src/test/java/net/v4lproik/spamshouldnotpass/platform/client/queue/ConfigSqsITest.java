package net.v4lproik.spamshouldnotpass.platform.client.queue;

import net.v4lproik.spamshouldnotpass.spring.SpringAppConfig;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
        classes = {
                SpringAppConfig.class,
                ConfigSqs.class
        })
@WebAppConfiguration
public class ConfigSqsITest {

    @Autowired
    EventBus eventBus;

    private static String QUEUE_URL = "global";

    @Before
    public void setUp(){
        eventBus.purgeAllQueues();
    }

    @Test
    public void test_ping_sqs(){
        eventBus.publish("test");
        List<Event> events = eventBus.pull(QUEUE_URL);

        assertEquals(1, events.size());
        assertEquals("\"test\"", events.get(0).getBody());
    }

    @After
    public void cleanUp(){
        eventBus.purgeAllQueues();
    }
}