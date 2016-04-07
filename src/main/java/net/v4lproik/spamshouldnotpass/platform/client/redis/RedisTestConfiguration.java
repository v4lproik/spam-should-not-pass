package net.v4lproik.spamshouldnotpass.platform.client.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.data.redis.connection.jedis.JedisConnection;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;

@Import(value = {
        ConfigRedis.class
})
public class RedisTestConfiguration {

    private final JedisConnectionFactory connectionFactory;

    @Autowired
    public RedisTestConfiguration(JedisConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    @Bean
    JedisConnection connection(){
        return connectionFactory.getConnection();
    }

}