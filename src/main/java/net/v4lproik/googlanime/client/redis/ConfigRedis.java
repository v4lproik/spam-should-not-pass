package net.v4lproik.googlanime.client.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.session.web.http.HeaderHttpSessionStrategy;
import org.springframework.session.web.http.HttpSessionStrategy;
import redis.clients.jedis.JedisPoolConfig;

@Configuration
@EnableRedisHttpSession
@PropertySources(@PropertySource("classpath:properties/app-${spring.profiles.active}.properties"))
public class ConfigRedis {

    @Autowired
    private Environment env;

    @Bean(destroyMethod = "destroy")
    public JedisConnectionFactory connectionFactory() {

        final String HOST = env.getRequiredProperty("redis.host");
        final Integer PORT = Integer.parseInt(env.getRequiredProperty("redis.port"));
        final Integer MAX_IDLE = Integer.parseInt(env.getRequiredProperty("redis.maxIdle"));
        final Integer MIN_IDLE = Integer.parseInt(env.getRequiredProperty("redis.minIdle"));
        final Integer MAX_TOTAL = Integer.parseInt(env.getRequiredProperty("redis.maxTotal"));
        final Integer TIMEOUT = Integer.parseInt(env.getRequiredProperty("redis.timeout"));
        final String TEST_ON_BORROW = env.getRequiredProperty("redis.testOnBorrow");

        JedisConnectionFactory connectionFactory = new JedisConnectionFactory();

        if (HOST == null || PORT == null){
            throw new IllegalArgumentException("Database host, user or password cannot be found. Check that the active profile provided a file that contains the variable mysql.host... ");
        }

        JedisPoolConfig configPool = new JedisPoolConfig();
        configPool.setMaxIdle(MAX_IDLE);
        configPool.setMinIdle(MIN_IDLE);
        configPool.setMaxTotal(MAX_TOTAL);
        if (Boolean.parseBoolean(TEST_ON_BORROW)){
            configPool.setTestOnBorrow(Boolean.parseBoolean(TEST_ON_BORROW));
        }

        connectionFactory.setPoolConfig(configPool);

        connectionFactory.setHostName(HOST);
        connectionFactory.setPort(PORT);
        connectionFactory.setTimeout(TIMEOUT);

        return connectionFactory;
    }

    @Bean
    public HttpSessionStrategy httpSessionStrategy() {
        return new HeaderHttpSessionStrategy();
    }
}


