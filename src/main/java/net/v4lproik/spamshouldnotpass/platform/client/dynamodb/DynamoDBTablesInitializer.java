package net.v4lproik.spamshouldnotpass.platform.client.dynamodb;

import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import org.springframework.beans.factory.annotation.Autowired;

public class DynamoDBTablesInitializer {

    private final DynamoDB dynamoDB;

    @Autowired
    public DynamoDBTablesInitializer(DynamoDB dynamoDB) {
        this.dynamoDB = dynamoDB;
    }

    public void createTables(){
        new BasicInfoTableInitializer(dynamoDB).createTable();
        new AuthorMessageTableInitializer(dynamoDB).createTable();
    }

    public void deleteTables(){
        new BasicInfoTableInitializer(dynamoDB).deleteTable();
        new AuthorMessageTableInitializer(dynamoDB).deleteTable();
    }
}
