package net.v4lproik.spamshouldnotpass.platform.client.dynamodb;

import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(value = {
        ConfigDynamoDB.class
})
public class DynamoDBTestConfiguration {

        @Autowired
        private DynamoDB dynamoDB;

        @Bean
        public DynamoDBTablesInitializer dynamoDBTablesInitializer(DynamoDB dynamoDB) throws Exception {
                return new DynamoDBTablesInitializer(dynamoDB);
        }
}