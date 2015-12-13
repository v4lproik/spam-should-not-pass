package net.v4lproik.spamshouldnotpass.platform.client.dynamodb;

import com.amazonaws.AmazonClientException;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static junit.framework.TestCase.fail;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
        classes = {
                DynamoDBTestConfiguration.class,
        })
public class DynamoDBITest {

    @Autowired
    DynamoDB dynamoDB;

    @Test
    public void test_ping_dynamo(){

        try {
            dynamoDB.getTable("test");
        } catch (AmazonClientException ace) {
            fail(ace.getMessage());
        }
    }
}