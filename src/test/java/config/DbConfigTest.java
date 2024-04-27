package config;

import model.Comment;
import model.Tag;
import model.Task;
import model.User;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.testcontainers.containers.PostgreSQLContainer;

import java.util.Properties;

public class DbConfigTest {
    private SessionFactory sessionFactory;

    public DbConfigTest(PostgreSQLContainer database) {
        configureSessionFactory(database);
    }

    private void configureSessionFactory(PostgreSQLContainer database) {
        this.sessionFactory = new Configuration()
                .addAnnotatedClass(User.class)
                .addAnnotatedClass(Tag.class)
                .addAnnotatedClass(Task.class)
                .addAnnotatedClass(Comment.class)
                .setProperties(setProperties(database))
                .buildSessionFactory();
    }

    private Properties setProperties(PostgreSQLContainer database) {
        Properties properties = new Properties();
        properties.setProperty(Environment.DIALECT, "org.hibernate.dialect.PostgreSQLDialect");
        properties.setProperty(Environment.DRIVER, "org.postgresql.Driver");
        properties.setProperty(Environment.URL, database.getJdbcUrl());
        properties.setProperty(Environment.USER, database.getUsername());
        properties.setProperty(Environment.PASS, database.getPassword());
        properties.setProperty(Environment.SHOW_SQL, "true");
        properties.setProperty(Environment.FORMAT_SQL, "true");
        properties.setProperty(Environment.HBM2DDL_AUTO, "none");
        return properties;
    }

    public SessionFactory get() {
        return this.sessionFactory;
    }
}
