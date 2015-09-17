package net.v4lproik.googlanime.client.mysql;

import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import javax.validation.constraints.NotNull;
import java.sql.Connection;
import java.sql.SQLException;

@Transactional
public class SqlDatabaseInitializer {

    @NotNull
    private final DataSource mysqlDataSource;

    @NotNull
    private final Environment env;


    public SqlDatabaseInitializer(final DataSource mysqlDataSource, final Environment env){
        this.mysqlDataSource = mysqlDataSource;
        this.env = env;
    }

    public void createDatabase() throws SQLException {

        DataSourceInitializer initializer = new DataSourceInitializer();

        initializer.setDataSource(mysqlDataSource);

        ResourceDatabasePopulator populator = new ResourceDatabasePopulator();

        populator.setIgnoreFailedDrops(true);

        populator.addScript(
                new ClassPathResource(env.getRequiredProperty("database.script"))
        );

        initializer.setDatabasePopulator(populator);

        Connection connection = null;

        try {
            connection = mysqlDataSource.getConnection();
            populator.populate(mysqlDataSource.getConnection());
        }
        finally {
            if (connection != null) {
                DataSourceUtils.releaseConnection(connection, mysqlDataSource);
            }
        }
    }
}
