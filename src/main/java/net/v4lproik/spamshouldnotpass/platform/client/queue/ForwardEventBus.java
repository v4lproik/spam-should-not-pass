package net.v4lproik.spamshouldnotpass.platform.client.queue;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.sqs.AmazonSQSClient;
import com.amazonaws.services.sqs.model.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class ForwardEventBus implements EventBus{

    private final AmazonSQSClient amazonSQSClient;
    private final ObjectMapper objectMapper;
    private final List<String> queues;

    private static final Logger log = LoggerFactory.getLogger(ForwardEventBus.class);

    public ForwardEventBus(AmazonSQSClient amazonSQSClient, ObjectMapper objectMapper, List<String> queues) {
        this.amazonSQSClient = amazonSQSClient;
        this.objectMapper = objectMapper;
        this.queues = queues;
    }

    @Override
    public void publish(Object event) {
        //TODO: Have to batch
        try {
            for (String queue:queues){
                amazonSQSClient.sendMessage(new SendMessageRequest(queue, objectMapper.writeValueAsString(event)));
            }
        } catch (JsonProcessingException e) {
            log.error(String.format("[ForwardEventBus] Can publish event %s", event.toString()));
        }
    }

    @Override
    public void publish(String queueUrl, Object event) {
        try {
            amazonSQSClient.sendMessage(new SendMessageRequest(queueUrl, objectMapper.writeValueAsString(event)));
        } catch (JsonProcessingException e) {
            log.error(String.format("[ForwardEventBus] Can publish event %s", event.toString()));
        }
    }

    @Override
    public List<Event> pull(String queueUrl) {
        ReceiveMessageRequest request = new ReceiveMessageRequest("global");
        ReceiveMessageResult receiveMessage = amazonSQSClient.receiveMessage(request);

        final List<Event> events = Lists.newArrayList();
        for (Message message:receiveMessage.getMessages()){
            events.add(new Event(message.getBody()));
        }

        return events;
    }

    @Override
    public void register(Object toRegister) {

    }

    @Override
    public void unregister(Object toUnregister) {
    }

    @Override
    public void purgeQueue(String queueUrl) {
        amazonSQSClient.purgeQueue(new PurgeQueueRequest(queueUrl));
    }

    @Override
    public void purgeAllQueues() {
        try{
            for (String queue:queues){
                amazonSQSClient.deleteQueue(queue);
            }
        } catch (AmazonServiceException ase) {
            log.error("Error when trying to create queues", ase);
        } catch (AmazonClientException ace) {
            log.error("Error when trying to create queues", ace);
        }

        createQueues();
    }


    @Override
    public void createQueues() {
        try{
            for (String queue:queues){
                amazonSQSClient.createQueue(queue);
            }
        } catch (AmazonServiceException ase) {
            log.error("Error when trying to create queues", ase);
        } catch (AmazonClientException ace) {
            log.error("Error when trying to create queues", ace);
        }
    }
}
