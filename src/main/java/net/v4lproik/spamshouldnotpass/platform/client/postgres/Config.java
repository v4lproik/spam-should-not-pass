package net.v4lproik.spamshouldnotpass.platform.client.postgres;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import net.v4lproik.spamshouldnotpass.platform.service.api.entities.User;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.core.env.Environment;

import javax.sql.DataSource;


@Configuration
@PropertySources(@PropertySource("classpath:properties/app-${spring.profiles.active}.properties"))
public class Config {

    private static final Logger log = LoggerFactory.getLogger(Config.class);

    @Autowired
    private Environment env;

    @Bean(destroyMethod = "close")
    public SessionFactory sessionFactoryConfig() {

        final String HOST = env.getRequiredProperty("database.host");
        final String DRIVER = env.getRequiredProperty("database.driver");
        final String PORT = env.getRequiredProperty("database.port");
        final String USER = env.getRequiredProperty("database.user");
        final String PWD = env.getRequiredProperty("database.password");
        final String DB = env.getRequiredProperty("database.db");

        //SET character_set_database='UTF8';
        //SET character_set_server='UTF8';

        String connectionURI = String.format("jdbc:postgresql://%s:%s/%s", HOST, PORT, DB);

        org.hibernate.cfg.Configuration c = new org.hibernate.cfg.Configuration();
        c.setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
        c.setProperty("hibernate.connection.driver_class", "org.postgresql.Driver");
        c.setProperty("hibernate.connection.url", connectionURI);
        c.setProperty("hibernate.connection.username", USER);
        c.setProperty("hibernate.connection.password", PWD);
        c.setProperty("hibernate.connection.autoReconnect", "true");
        c.setProperty("hibernate.current_session_context_class", "thread");
        c.addPackage("net.v4lproik.spamshouldnotpass");
        c.addAnnotatedClass(User.class);

        c.setProperty("connection.provider_class", "org.hibernate.connection.C3P0ConnectionProvider");
        c.setProperty("c3p0.min_size", "5");
        c.setProperty("c3p0.max_size", "20");
        c.setProperty("c3p0.timeout", "1800");
        c.setProperty("c3p0.max_statements", "100");
        c.setProperty("hibernate.c3p0.testConnectionOnCheckout", "true");

        StandardServiceRegistryBuilder ssrb = new StandardServiceRegistryBuilder().applySettings(c.getProperties());
        SessionFactory sessionFactory = c.buildSessionFactory(ssrb.build());

        return sessionFactory;
    }

    @Bean
    public DataSource mysqlDataSource() {

        MysqlDataSource mysqlDS = null;
        final String HOST = env.getRequiredProperty("database.host");
        final String DRIVER = env.getRequiredProperty("database.driver");
        final String PORT = env.getRequiredProperty("database.port");
        final String USER = env.getRequiredProperty("database.user");
        final String PWD = env.getRequiredProperty("database.password");
        final String DB = env.getRequiredProperty("database.db");

        mysqlDS = new MysqlDataSource();
        mysqlDS.setURL(String.format("jdbc:%s://%s:%s/%s", DRIVER, HOST, PORT, DB));
        mysqlDS.setUser(USER);
        mysqlDS.setPassword(PWD);

        return mysqlDS;
    }
}