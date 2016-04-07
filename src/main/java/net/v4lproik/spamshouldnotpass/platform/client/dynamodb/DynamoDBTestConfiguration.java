package net.v4lproik.spamshouldnotpass.platform.client.dynamodb;

import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

@Import({ConfigDynamoDB.class})
public class DynamoDBTestConfiguration {

    private final DynamoDB dynamoDB;

    @Autowired
    public DynamoDBTestConfiguration(DynamoDB dynamoDB) {
        this.dynamoDB = dynamoDB;
    }

    @Bean
    public DynamoDBTablesInitializer dynamoDBTablesInitializer() throws Exception {
        return new DynamoDBTablesInitializer(dynamoDB);
    }
}