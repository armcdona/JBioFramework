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

    private final static Logger logger = LoggerFactory.getLogger(DatabaseUtilities.class);

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

    private static boolean doesDatabaseExist(String fileName) {
        boolean exists = false;
        return exists;
    }

    private static String constructSQLTableCreationCommand(String tableName, Vector<String> columnNames,
                                                           Vector<String> columnParameters, Vector<String> columnTypes) {
        String sqlCommand = "";
        sqlCommand+="CREATE TABLE "+tableName+" (";
        for (int i=0; (i < columnNames.size()); i++) {
            sqlCommand+=columnNames.get(i)+" "+columnTypes.get(i)+" "+columnParameters.get(i);
            if (i != (columnNames.size() - 1)) {
                sqlCommand+=",";
            }
        }
        sqlCommand+=") WITHOUT ROWID;";
        return sqlCommand;
    }

    private static String constructSQLProteinCreationCommand(String tableName, Vector<String> columnNames,
                                                            Vector<String> columnParameters, Vector<String> columnTypes,
                                                            Protein protein) {
        String sqlCommand = "INSERT INTO "+tableName+"(";
        for (String name : columnNames) {
            sqlCommand+=name;
            if (!(name == columnNames.get(columnNames.size() - 1))) {
                sqlCommand+=",";
            }
        }
        sqlCommand+=") VALUES(";
        for (int i=0;i< (columnNames.size()-1);i++) {
            sqlCommand+="?";
            if (i!= (columnNames.size() -1 )) {
                sqlCommand+=",";
            }
        }
        sqlCommand+=")";
        return sqlCommand;
    }

    private static String constructSQLProteinRemovalCommand(String tableName, Vector<String> columnNames,
                                                             Vector<String> columnParameters, Vector<String> columnTypes,
                                                             Protein protein) {
        String sqlCommand = "";
        return sqlCommand;
    }

    private static String constructSQLProteinRetrieveCommand(String tableName, Vector<String> columnNames,
                                                             Vector<String> columnParameters, Vector<String> columnTypes,
                                                             Protein protein) {
        String sqlCommand = "";
        return sqlCommand;
    }

    private static Vector<String> getColumnNames() {
        Vector<String> columnNames = new Vector<>();
        columnNames.add("sequence");
        columnNames.add("name");
        columnNames.add("functions");
        columnNames.add("molecular_weight");
        columnNames.add("isoelectric_point");
        //columnNames.add("");
        return columnNames;
    }

    private static Vector<String> getcolumnTypes() {
        Vector<String> columnTypes = new Vector<>();
        columnTypes.add("text");
        columnTypes.add("text");
        columnTypes.add("text");
        columnTypes.add("real");
        columnTypes.add("real");
        //columnTypes.add("");
        return columnTypes;
    }

    private static Vector<String> getColumnParameters() {
        Vector<String> columnParameters = new Vector<>();
        columnParameters.add("PRIMARY KEY UNIQUE NOT NULL");
        columnParameters.add("NOT NULL");
        columnParameters.add("NOT NULL");
        columnParameters.add("NOT NULL");
        columnParameters.add("NOT NULL");
        //columnParameters.add("");
        return columnParameters;
    }

}
