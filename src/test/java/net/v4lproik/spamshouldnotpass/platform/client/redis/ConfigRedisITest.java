package net.v4lproik.spamshouldnotpass.platform.client.redis;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
        classes = {
                RedisTestConfiguration.class,
        })
public class ConfigRedisITest {

    @Autowired
    ConfigRedis configRedis;

    @Test
    public void test_ping_redis(){
        configRedis.connectionFactory().getConnection().ping();
    }

}