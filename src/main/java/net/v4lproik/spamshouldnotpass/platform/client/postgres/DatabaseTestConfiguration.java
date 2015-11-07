package net.v4lproik.spamshouldnotpass.platform.client.postgres;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import javax.sql.DataSource;

@Configuration
@Import({Config.class})
public class DatabaseTestConfiguration {

    @Autowired
    org.springframework.core.env.Environment env;

    @Bean
    public SqlDatabaseInitializer databaseInitializer(DataSource mysqlDataSource) throws Exception {

        return new SqlDatabaseInitializer(mysqlDataSource, env);
    }
}
