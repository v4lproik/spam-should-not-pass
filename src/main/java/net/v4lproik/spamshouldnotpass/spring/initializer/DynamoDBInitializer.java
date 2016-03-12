package net.v4lproik.spamshouldnotpass.spring.initializer;

import net.v4lproik.spamshouldnotpass.platform.client.dynamodb.DynamoDBTablesInitializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

public class DynamoDBInitializer implements ApplicationListener<ContextRefreshedEvent> {

    private final DynamoDBTablesInitializer dynamoDBTablesInitializer;

    @Autowired
    public DynamoDBInitializer(DynamoDBTablesInitializer dynamoDBTablesInitializer) {
        this.dynamoDBTablesInitializer = dynamoDBTablesInitializer;
    }

    @Override
    public void onApplicationEvent(final ContextRefreshedEvent event) {
        dynamoDBTablesInitializer.createTables();
    }
}