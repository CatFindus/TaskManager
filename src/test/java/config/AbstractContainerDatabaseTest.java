package config;

import liquibase.Contexts;
import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.resource.ClassLoaderResourceAccessor;
import lombok.SneakyThrows;
import org.hibernate.SessionFactory;
import org.jetbrains.annotations.NotNull;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public abstract class AbstractContainerDatabaseTest {

    @Container
    protected PostgreSQLContainer<?> database = new PostgreSQLContainer<>("postgres:16.2-alpine")
            .withDatabaseName("test")
            .withUsername("root")
            .withPassword("root")
            .withExposedPorts(5432)
            .withInitScript("initTestContainer.sql");
    protected SessionFactory getFactory() {
        return new DbConfigTest(database).get();
    }

    @SneakyThrows
    protected void init() {
        database.start();
        try(Connection connection = DriverManager.getConnection(database.getJdbcUrl(), getProperties())) {
            Database base = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(new JdbcConnection(connection));
            Liquibase liquibase = new Liquibase("db/changelog/changelog.xml", new ClassLoaderResourceAccessor(), base);

            liquibase.update(new Contexts());
        }
    }

    private @NotNull Properties getProperties() {
        Properties properties = new Properties();
        properties.setProperty("user", database.getUsername());
        properties.setProperty("password", database.getPassword());
        properties.setProperty("currentSchema", "main_schema");
        return properties;
    }

}
