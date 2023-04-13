package ru.tinkoff.edu.java;

import liquibase.Contexts;
import liquibase.LabelExpression;
import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.LiquibaseException;
import liquibase.resource.ClassLoaderResourceAccessor;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.GenericContainer;

import java.sql.DriverManager;
import java.sql.SQLException;

public class IntegrationEnvironment {
    @Test
    void test() throws LiquibaseException {



        try (GenericContainer container = new GenericContainer("postgres")) {
            container.start();
            java.sql.Connection connection = DriverManager.getConnection("jdbc:postgresql://postgresql:5432/scrapper", "postgres", "postgres");
            Database database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(new JdbcConnection(connection));
            Liquibase liquibase = new liquibase.Liquibase("migration/migrations/master.xml", new ClassLoaderResourceAccessor(), database);
            liquibase.update(new Contexts(), new LabelExpression());


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

}
