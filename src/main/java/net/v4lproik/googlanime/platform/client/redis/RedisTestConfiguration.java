package net.v4lproik.googlanime.platform.client.redis;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(value = {
        ConfigRedis.class
})
public class RedisTestConfiguration {}