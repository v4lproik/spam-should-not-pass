package net.v4lproik.spamshouldnotpass.platform.client.postgres;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.core.env.Environment;

import javax.sql.DataSource;

@Import({Config.class})
public class DatabaseTestConfiguration {

    private final org.springframework.core.env.Environment env;

    @Autowired
    public DatabaseTestConfiguration(Environment env) {
        this.env = env;
    }

    @Bean
    public SqlDatabaseInitializer databaseInitializer(DataSource mysqlDataSource) throws Exception {
        return new SqlDatabaseInitializer(mysqlDataSource, env);
    }
}
