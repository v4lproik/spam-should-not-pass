package net.v4lproik.spamshouldnotpass.spring;

import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.v4lproik.spamshouldnotpass.platform.client.dynamodb.ConfigDynamoDB;
import net.v4lproik.spamshouldnotpass.platform.client.dynamodb.DynamoDBTablesInitializer;
import net.v4lproik.spamshouldnotpass.platform.client.elasticsearch.ConfigES;
import net.v4lproik.spamshouldnotpass.platform.client.postgres.Config;
import net.v4lproik.spamshouldnotpass.platform.client.queue.ConfigSqs;
import net.v4lproik.spamshouldnotpass.platform.client.queue.EventBus;
import net.v4lproik.spamshouldnotpass.platform.repositories.UserRepository;
import net.v4lproik.spamshouldnotpass.spring.initializer.DynamoDBInitializer;
import net.v4lproik.spamshouldnotpass.spring.interceptor.AuthorisationApiKeyInterceptor;
import net.v4lproik.spamshouldnotpass.spring.interceptor.AuthorisationSessionInterceptor;
import net.v4lproik.spamshouldnotpass.spring.interceptor.LoggerEndpointInterceptor;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
@ComponentScan("net.v4lproik.spamshouldnotpass")
@EnableWebMvc
public class SpringAppConfig extends WebMvcConfigurerAdapter {

    @Autowired
    public Environment env;

    @Autowired
    public UserRepository userRepository;

    //=========== MAPPER ===========//
    @Bean
    public ExpressionParser parser() {
        return new SpelExpressionParser();
    }

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }


    //=========== CLIENT ===========//
    @Bean
    public Config config(){
        return new Config(env);
    }

    @Bean
    public ConfigSqs configSqs(ObjectMapper objectMapper){
        return new ConfigSqs(env, objectMapper);
    }

    @Bean
    public EventBus eventBus(ConfigSqs configSqs){
        return configSqs.eventBus();
    }

    @Bean
    public SessionFactory sessionFactory(Config config){
        return config.sessionFactoryConfig();
    }

    @Bean
    public ConfigES configES(){
        return new ConfigES(env);
    }

    @Bean
    public ConfigDynamoDB configDynamoDB(){
        return new ConfigDynamoDB(env);
    }

    @Bean
    public DynamoDB dynamoDB(ConfigDynamoDB configDynamoDB){
        return configDynamoDB.dynamoDB();
    }

    //=========== INTERCEPTOR ===========//
    @Bean
    public AuthorisationSessionInterceptor authenticationInterceptor() {
        return new AuthorisationSessionInterceptor();
    }

    @Bean
    public LoggerEndpointInterceptor loggerEndpointInterceptor() {
        return new LoggerEndpointInterceptor();
    }

    @Bean
    public AuthorisationApiKeyInterceptor authorisationApiKeyInterceptor(UserRepository userRepository){
        return new AuthorisationApiKeyInterceptor(userRepository);
    }


    //=========== INITIALIZER ===========//
    @Bean
    public DynamoDBInitializer dynamoDBInitializer(DynamoDB dynamoDB){
        return new DynamoDBInitializer(new DynamoDBTablesInitializer(dynamoDB));
    }


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loggerEndpointInterceptor());
        registry.addInterceptor(authorisationApiKeyInterceptor(userRepository));
        registry.addInterceptor(authenticationInterceptor());
    }
}
