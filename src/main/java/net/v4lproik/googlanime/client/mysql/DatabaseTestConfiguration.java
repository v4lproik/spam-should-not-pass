package net.v4lproik.googlanime.client.mysql;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import javax.sql.DataSource;

@Configuration
@Import({ConfigMysql.class})
public class DatabaseTestConfiguration {

    @Autowired
    org.springframework.core.env.Environment env;

    @Bean
    public SqlDatabaseInitializer databaseInitializer(DataSource mysqlDataSource) throws Exception {

        return new SqlDatabaseInitializer(mysqlDataSource, env);
    }
}
