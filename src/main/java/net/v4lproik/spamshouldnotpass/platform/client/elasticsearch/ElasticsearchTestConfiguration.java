package net.v4lproik.spamshouldnotpass.platform.client.elasticsearch;


import org.elasticsearch.client.Client;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({ConfigES.class})
public class ElasticsearchTestConfiguration {

    @Bean
    public ElasticsearchIndexInitializer elasticsearchIndexInitializer(Client client){
        return new ElasticsearchIndexInitializer(client);
    }
}
