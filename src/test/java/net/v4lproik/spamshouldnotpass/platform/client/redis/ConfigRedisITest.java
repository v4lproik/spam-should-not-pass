package net.v4lproik.spamshouldnotpass.platform.client.redis;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.jedis.JedisConnection;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
        classes = {
                RedisTestConfiguration.class,
        })
@WebAppConfiguration
public class ConfigRedisITest {

    @Autowired
    public JedisConnection connection;

    @Test
    public void test_ping_redis(){
        connection.ping();
    }
}