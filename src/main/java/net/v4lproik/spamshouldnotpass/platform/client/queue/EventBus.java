package net.v4lproik.spamshouldnotpass.platform.client.queue;

import java.util.List;

public interface EventBus {
    public void publish(Object event);
    public void publish(String queueUrl, Object event);
    public List<Event> pull(String queueUrl);
    public void register(Object toRegister);
    public void unregister(Object toUnregister);

    public void purgeQueue(String queueUrl);
    public void purgeAllQueues();
    public void createQueues();
}
