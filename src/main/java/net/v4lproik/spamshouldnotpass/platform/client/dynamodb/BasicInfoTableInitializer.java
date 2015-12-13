package net.v4lproik.spamshouldnotpass.platform.client.dynamodb;

import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.model.*;
import org.apache.log4j.Logger;

import java.util.Arrays;

public final class BasicInfoTableInitializer {

    private static Logger log = Logger.getLogger(BasicInfoTableInitializer.class);

    private final DynamoDB dynamoDB;

    private final String TABLE_NAME = "BasicInfo";

    public BasicInfoTableInitializer(DynamoDB dynamoDB) {
        this.dynamoDB = dynamoDB;
    }

    protected void createTable(){

        try{
            dynamoDB.getTable(TABLE_NAME).describe();
        }catch (ResourceNotFoundException e){
            Table table = dynamoDB.createTable(TABLE_NAME,
                    Arrays.asList(
                            new KeySchemaElement("id", KeyType.HASH)
                    ),
                    Arrays.asList(
                            new AttributeDefinition("id", ScalarAttributeType.S)
                    ),
                    new ProvisionedThroughput(1L, 1L));

            try {
                table.waitForActive();
            } catch (InterruptedException e1) {

            }

            log.info("Table status: " + table.getDescription().getTableStatus());
        }
    }

    protected void deleteTable(){

        try{
            dynamoDB.getTable(TABLE_NAME).delete();
        }catch (ResourceNotFoundException e){
        }
    }
}
