package net.v4lproik.spamshouldnotpass.platform.client.dynamodb;

import com.amazonaws.services.dynamodbv2.document.DynamoDB;

public class DynamoDBTablesInitializer {

    private DynamoDB dynamoDB;

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
