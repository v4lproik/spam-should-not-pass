package net.v4lproik.spamshouldnotpass.platform.client;

import net.v4lproik.spamshouldnotpass.platform.client.dynamodb.DynamoDBTestConfiguration;
import net.v4lproik.spamshouldnotpass.platform.client.elasticsearch.ElasticsearchTestConfiguration;
import net.v4lproik.spamshouldnotpass.platform.client.postgres.DatabaseTestConfiguration;
import net.v4lproik.spamshouldnotpass.platform.client.redis.RedisTestConfiguration;
import org.springframework.context.annotation.Import;

@Import(value = {
        DatabaseTestConfiguration.class,
        ElasticsearchTestConfiguration.class,
        DynamoDBTestConfiguration.class,
        RedisTestConfiguration.class
})
public class ClientTestConfiguration {}