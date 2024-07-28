package org.healthcare.config;

import liquibase.Contexts;
import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.LiquibaseException;
import liquibase.resource.ClassLoaderResourceAccessor;
import liquibase.resource.ResourceAccessor;

import java.sql.Connection;

public class LiquibaseManager {
    private final String changelogFile;

    public LiquibaseManager(ConfigLoader configLoader) {
        this.changelogFile = configLoader.getProperty("liquibase.changelog");
    }

    public void executarLiquibase(Connection connection) throws LiquibaseException {
        Database database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(new JdbcConnection(connection));

        ResourceAccessor resourceAccessor = new ClassLoaderResourceAccessor();
        Liquibase liquibase = new Liquibase(changelogFile, resourceAccessor, database);
        liquibase.update(new Contexts());
    }
}
