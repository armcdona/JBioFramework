package org.jbioframework.library.utilities;

import org.jbioframework.library.protein.Protein;

import java.io.File;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Vector;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sqlite.*;


public class DatabaseUtilities {

    final static Logger logger = LoggerFactory.getLogger(DatabaseUtilities.class);

    public static Vector<Protein> loadDatabase(File databaseFile) {
        Vector<Protein> proteins = new Vector<Protein>();
        Connection databaseConnection = connect(databaseFile.getAbsolutePath());
        return proteins;
    }

    public static void saveDatabase(Vector<Protein> proteins) {
    }

    private static Connection connect(String absoluteFilePath) {
        String db_URL = "jdbc:sqlite:" + absoluteFilePath;
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(db_URL);
            if (connection == null) {
                logger.error("Could not connect to sqlite database "+absoluteFilePath);
            } else {
                logger.info("Successfully connected to sqlite database "+absoluteFilePath);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
        return connection;
    }

    private boolean doesDatabaseExist(String fileName) {
        boolean exists = false;
        return exists;
    }

    private static String constructSQLTableCreationCommand(String tableName, Vector<String> columnName,
                                                           Vector<String> columnParameters, Vector<String> columnType) {
        String sqlCommand = "";
        return sqlCommand;
    }

}
