package net.v4lproik.spamshouldnotpass.platform.client.postgres;

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
    private final DataSource postgresDataSource;

    @NotNull
    private final Environment env;

    private final DataSourceInitializer initializer;

    public SqlDatabaseInitializer(final DataSource postgresDataSource, final Environment env){
        this.postgresDataSource = postgresDataSource;
        this.env = env;
        this.initializer = new DataSourceInitializer();
        initializer.setDataSource(postgresDataSource);
    }

    public void createDatabase() throws SQLException {

        ResourceDatabasePopulator populator = new ResourceDatabasePopulator();

        populator.setIgnoreFailedDrops(true);

        populator.addScript(
                new ClassPathResource(env.getRequiredProperty("database.script"))
        );

        initializer.setDatabasePopulator(populator);

        Connection connection = null;

        try {
            connection = postgresDataSource.getConnection();
            populator.populate(postgresDataSource.getConnection());
        }
        finally {
            if (connection != null) {
                DataSourceUtils.releaseConnection(connection, postgresDataSource);
            }
        }
    }

    public void flushAllData() throws SQLException {

        ResourceDatabasePopulator populator = new ResourceDatabasePopulator();

        populator.setIgnoreFailedDrops(false);

        populator.addScript(
                new ClassPathResource(String.format("%s.drop", env.getRequiredProperty("database.script")))
        );

        initializer.setDatabasePopulator(populator);

        Connection connection = null;

        try {
            connection = postgresDataSource.getConnection();
            populator.populate(postgresDataSource.getConnection());
        }

        finally {
            if (connection != null) {
                DataSourceUtils.releaseConnection(connection, postgresDataSource);
            }
        }
    }
}
