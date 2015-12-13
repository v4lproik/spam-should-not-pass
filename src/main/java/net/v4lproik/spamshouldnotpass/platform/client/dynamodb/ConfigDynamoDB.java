package net.v4lproik.spamshouldnotpass.platform.client.dynamodb;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.core.env.Environment;

@Configuration
@PropertySources(@PropertySource("classpath:properties/app-${spring.profiles.active}.properties"))
public class ConfigDynamoDB {

    @Autowired
    private Environment env;

    @Bean
    public DynamoDB dynamoDB() {

        final String ACCESS_KEY = env.getRequiredProperty("aws.dynamodb.accessKey");
        final String SECRET_KEY = env.getRequiredProperty("aws.dynamodb.secretKey");
        final String ENDPOINT = env.getProperty("aws.dynamodb.endpoint");

        AWSCredentials credentials = new BasicAWSCredentials(ACCESS_KEY, SECRET_KEY);
        AmazonDynamoDBClient client = new AmazonDynamoDBClient(credentials);

        if (ENDPOINT != null) {
            client.setEndpoint(ENDPOINT);
        }

        DynamoDB dynamoDB = new DynamoDB(client);

        return  dynamoDB;
    }
}


