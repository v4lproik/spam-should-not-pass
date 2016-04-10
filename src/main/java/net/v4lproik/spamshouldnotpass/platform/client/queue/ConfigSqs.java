package net.v4lproik.spamshouldnotpass.platform.client.queue;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.sqs.AmazonSQSClient;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.core.env.Environment;

import java.util.List;


@PropertySources(@PropertySource("classpath:properties/app-${spring.profiles.active}.properties"))
public class ConfigSqs {

    private final Environment env;
    private final QueueFactory queueFactory;
    private final ObjectMapper objectMapper;

    private static final List<String> QUEUES = Lists.newArrayList("global");

    enum QueueDriver {
        INMEMORY("inmemory"),
        SQS("sqs");

        private final String name;

        QueueDriver(String name) {
            this.name = name;
        }

        public static QueueDriver fromString(String nameToCheck) {
            if (nameToCheck != null) {
                for (QueueDriver driver : QueueDriver.values()) {
                    if (nameToCheck.equalsIgnoreCase(driver.name)) {
                        return driver;
                    }
                }
            }
            return null;
        }
    }

    @Autowired
    public ConfigSqs(Environment env, ObjectMapper objectMapper) {
        this.env = env;
        this.queueFactory = new QueueFactory();
        this.objectMapper = objectMapper;
    }

    private AmazonSQSClient amazonSQS() {

        final String ACCESS_KEY = env.getRequiredProperty("aws.accessKey");
        final String SECRET_KEY = env.getRequiredProperty("aws.secretKey");
        final String ENDPOINT = env.getProperty("aws.sqs.endpoint");

        AWSCredentials credentials = new BasicAWSCredentials(ACCESS_KEY, SECRET_KEY);
        AmazonSQSClient client = new AmazonSQSClient(credentials);

        if (ENDPOINT != null) {
            client.setEndpoint(ENDPOINT);
        }

        return client;
    }

    private com.google.common.eventbus.EventBus guavaClient() {
        return new com.google.common.eventbus.EventBus();
    }

    @Bean
    public EventBus eventBus() {
        final String DRIVER = env.getRequiredProperty("queue.driver");

        EventBus eventBus = queueFactory.get(QueueDriver.fromString(DRIVER));

        eventBus.createQueues();

        return eventBus;
    }

    private class QueueFactory{

        public EventBus get(QueueDriver driver){
            switch (driver){
                case INMEMORY:
                    return new InMemoryEventBus(guavaClient(), objectMapper);

                case SQS:
                    return new ForwardEventBus(amazonSQS(), objectMapper, QUEUES);

                default:
                    throw new IllegalArgumentException(String.format("Queue driver %s does not exist", driver.toString()));
            }
        }
    }
}