package net.v4lproik.spamshouldnotpass.platform.client.queue;

import com.amazonaws.services.sqs.model.UnsupportedOperationException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import com.google.common.eventbus.Subscribe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class InMemoryEventBus implements EventBus{

    private final com.google.common.eventbus.EventBus eventBus;
    private final ObjectMapper objectMapper;

    private static final List<Object> events = Lists.newArrayList();
    private static final Logger log = LoggerFactory.getLogger(InMemoryEventBus.class);

    public InMemoryEventBus(com.google.common.eventbus.EventBus eventBus, ObjectMapper objectMapper) {
        this.eventBus = eventBus;
        this.objectMapper = objectMapper;

        eventBus.register(this);
    }

    @Override
    public void publish(Object event) {
        try {
            eventBus.post(objectMapper.writeValueAsString(event));
        } catch (JsonProcessingException e) {
            log.error(String.format("[InMemoryBus] Can publish event %s", event.toString()));
        }
    }

    @Override
    public void publish(String queueUrl, Object event) {
        throw new UnsupportedOperationException("Publish without queueUrl is not supported yet");
    }

    @Override
    public List<Event> pull(String queueUrl) {
        final List<Event> eventsList = Lists.newArrayList();

        for (Object event:events){
            eventsList.add(new Event(event.toString()));
        }

        return eventsList;
    }

    @Override
    public void register(Object toRegister) {
        eventBus.register(toRegister);
    }

    @Override
    public void unregister(Object toUnregister) {
        eventBus.unregister(toUnregister);
    }

    @Override
    public void createQueues() {}

    @Override
    public void purgeQueue(String queueUrl) {
        events.clear();
    }

    @Override
    public void purgeAllQueues() {
        events.clear();
    }

    @Subscribe
    public void getPublishedData(Object event){
        events.add(event);
    }
}
