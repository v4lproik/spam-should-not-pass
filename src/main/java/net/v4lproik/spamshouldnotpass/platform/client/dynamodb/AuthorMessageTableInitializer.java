package net.v4lproik.spamshouldnotpass.platform.client.dynamodb;

import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.model.*;
import org.apache.log4j.Logger;

import java.util.Arrays;

public final class AuthorMessageTableInitializer {

    private static Logger log = Logger.getLogger(AuthorMessageTableInitializer.class);

    private final DynamoDB dynamoDB;

    public static final String TABLE_NAME = "AuthorMessages";
    public static final String HASH_NAME = "id";
    public static final String RANGE_NAME = "date_corp";
    public static final String INFO_NAME = "info";

    public AuthorMessageTableInitializer(DynamoDB dynamoDB) {
        this.dynamoDB = dynamoDB;
    }

    protected void createTable(){

        try{
            Table table = dynamoDB.getTable(TABLE_NAME);
            table.describe();
        }catch (ResourceNotFoundException e){
            Table table = dynamoDB.createTable(TABLE_NAME,
                    Arrays.asList(
                            new KeySchemaElement(HASH_NAME, KeyType.HASH),
                            new KeySchemaElement(RANGE_NAME, KeyType.RANGE)
                    ),
                    Arrays.asList(
                            new AttributeDefinition(HASH_NAME, ScalarAttributeType.S),
                            new AttributeDefinition(RANGE_NAME, ScalarAttributeType.S)
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
