package net.v4lproik.googlanime.client.elasticsearch;

import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.core.env.Environment;

@Configuration
@PropertySources(@PropertySource("classpath:properties/app-${spring.profiles.active}.properties"))
public class Config {

    private static final Logger log = LoggerFactory.getLogger(Config.class);

    @Autowired
    private Environment env;

    @Bean(destroyMethod = "close")
    public TransportClient node() {
        Settings settings = ImmutableSettings.settingsBuilder()
                .put("cluster.name", "elasticsearch").build();

        final String ELASTICSEARCH_HOST = env.getProperty("elasticsearch.host");

        if (ELASTICSEARCH_HOST == null)
            throw new IllegalArgumentException("ElasticSearch host cannot be found. Check that the active profile provided a file that contains the variable elasticsearch.host ");

        return new TransportClient(settings).addTransportAddress(new InetSocketTransportAddress(ELASTICSEARCH_HOST, 9300));
    }

    @Bean
    public Client client() {
        return node();
    }

}