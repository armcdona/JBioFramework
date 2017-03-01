package org.jbioframework.library.utilities;

import org.jbioframework.library.protein.Protein;

import java.io.File;
import java.sql.*;
import java.util.Vector;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class DatabaseUtilities {

    private final static Logger logger = LoggerFactory.getLogger(DatabaseUtilities.class);

    public static void deleteDatabaseFile(String fileName) {
        String path = getDatabaseRelativePath(fileName);
        File databaseFile = new File(path);
        databaseFile.delete();

    }

    public static Vector<Protein> loadDatabase(String filename) {
        Vector<Protein> proteins = new Vector<Protein>();
        try {
            Connection databaseConnection = connect(getDatabaseRelativePath(filename));
            Statement sqlStatement = databaseConnection.createStatement();
            String sqlCommand = constructSQLProteinRetrieveAllCommand(filename,getColumnNames(),getColumnParameters(),getcolumnTypes());
            ResultSet proteinResultSet = sqlStatement.executeQuery(sqlCommand);
            int numProteins = 0;
            while (proteinResultSet.next()) {
                proteins.add(createProteinfromResultSet(proteinResultSet));
                numProteins++;
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return proteins;
    }

    public static void saveDatabase(Vector<Protein> proteins, String fileName) {
        try {
            Connection databaseConnection = connect(getDatabaseRelativePath(fileName));
            Statement sqlStatement = databaseConnection.createStatement();
            String sqlCreateTableCommand = constructSQLTableCreationCommand(fileName,getColumnNames(),getColumnParameters(),getcolumnTypes());
            sqlStatement.execute(sqlCreateTableCommand);


            databaseConnection.setAutoCommit(false);
            for (Protein protein : proteins) {
                String tableName = fileName;
                String sqlInsertCommand = constructSQLProteinCreationCommand(tableName, getColumnNames(), getColumnParameters(), getcolumnTypes(), protein);
                sqlStatement.execute(sqlInsertCommand);
            }
            sqlStatement.close();
            databaseConnection.commit();
            databaseConnection.close();
            logger.info("Successfully wrote proteins to "+fileName+".db!");
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
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

    public static boolean doesDatabaseExist(String fileName) {
        boolean exists = false;
        File database = new File(getDatabaseRelativePath(fileName));
        if (database.exists()) {
            exists = true;
        }
        return exists;
    }

    private static String constructSQLTableCreationCommand(String tableName, Vector<String> columnNames,
                                                           Vector<String> columnParameters, Vector<String> columnTypes) {
        String sqlCommand = "";
        sqlCommand+="CREATE TABLE "+tableName+" (";
        for (int i=0; (i < columnNames.size()); i++) {
            sqlCommand+=columnNames.get(i)+" "+columnTypes.get(i)+" "+columnParameters.get(i);
            if (i != (columnNames.size() - 1)) {
                sqlCommand+=", ";
            } else {
                sqlCommand+=" ";
            }
        }
        sqlCommand+=");";
        return sqlCommand;
    }

    private static String constructSQLProteinCreationCommand(String tableName, Vector<String> columnNames,
                                                            Vector<String> columnParameters, Vector<String> columnTypes,
                                                            Protein protein) {
        String sqlCommand = "INSERT INTO "+tableName+"(";
        for (String columnName : columnNames) {
            sqlCommand+=columnName;
            if (!(columnName == columnNames.get(columnNames.size() - 1))) {
                sqlCommand+=",";
            }
        }
        sqlCommand+=") VALUES(";
        Vector<String> proteinValues = getProteinValues(protein);
        int columnNumber = 0;
        for (String proteinValue : proteinValues) {
            if (proteinValue.contains(" ") || columnTypes.get(columnNumber).toLowerCase().contains("text")) {
                sqlCommand+="\""+proteinValue+"\"";
            } else {
                sqlCommand+=proteinValue;
            }
            if (!(proteinValue == proteinValues.get(columnNames.size() - 1))) {
                sqlCommand+=",";
            } else {
                sqlCommand+=" ";
            }
            columnNumber++;
        }
        sqlCommand+=");";
        return sqlCommand;
    }

    private static String constructSQLProteinRemovalCommand(String tableName, Vector<String> columnNames,
                                                             Vector<String> columnParameters, Vector<String> columnTypes,
                                                             String sequence) {
        String sqlCommand = "";
        sqlCommand+="DELETE from "+tableName+" WHERE ";
        int primaryColumnIndex = findPrimaryColumnIndex(columnParameters,tableName);
        if (columnTypes.get(primaryColumnIndex).toLowerCase().contains("text")) {
            sqlCommand+= columnNames.get(primaryColumnIndex) + "= \""+sequence + "\";";
        } else {
            sqlCommand+= columnNames.get(primaryColumnIndex) + "=" + sequence + ";";
        }
        return sqlCommand;
    }

    private static String constructSQLProteinRetrieveAllCommand(String tableName, Vector<String> columnNames,
                                                                Vector<String> columnParameters, Vector<String> columnTypes) {
        String sqlCommand = "";
        sqlCommand+="SELECT ";
        for (String columnName : columnNames) {
            sqlCommand+=columnName;
            if (!(columnName == columnNames.get(columnNames.size() - 1))) {
                sqlCommand+=", ";
            } else {
                sqlCommand+=" ";
            }
        }
        sqlCommand+="FROM "+tableName+";";
        return sqlCommand;
    }

    private static String constructSQLProteinRetrieveSpecificCommand(String tableName, Vector<String> columnNames,
                                                                Vector<String> columnParameters, Vector<String> columnTypes,
                                                                String sequence) {
        String sqlCommand = "";
        sqlCommand+="SELECT ";
        for (String columnName : columnNames) {
            sqlCommand+=columnName;
            if (!(columnName == columnNames.get(columnNames.size() - 1))) {
                sqlCommand+=", ";
            } else {
                sqlCommand+=" ";
            }
        }
        sqlCommand+="FROM "+tableName+" WHERE ";
        int primaryColumnIndex = findPrimaryColumnIndex(columnParameters,tableName);
        if (columnTypes.get(primaryColumnIndex).toLowerCase().contains("text")) {
            sqlCommand+= columnNames.get(primaryColumnIndex) + "= \""+sequence + "\";";
        } else {
            sqlCommand+= columnNames.get(primaryColumnIndex) + "=" + sequence + ";";
        }
        return sqlCommand;
    }

    private static int findPrimaryColumnIndex(Vector<String> columnParameters, String tableName) {
        int primaryColumnIndex = 0;
        for (int i=0;i<columnParameters.size() - 1;i++) {
            if (columnParameters.get(i).toUpperCase().contains("PRIMARY") && columnParameters.get(i).toUpperCase().contains("KEY")) {
                primaryColumnIndex = i;
            } else {
                logger.error("Could not find the primary key column in "+tableName+" table! Assuming first column is primary.");
            }
        }
        return primaryColumnIndex;
    }

    private static String getDatabaseRelativePath(String fileName) {
        return ((new Configuration().getProperty(ConfigurationConstants.DATABASE_FOLDER))+fileName+".db");
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
        columnParameters.add("NOT NULL");
        columnParameters.add("");
        columnParameters.add("");
        columnParameters.add("NOT NULL");
        columnParameters.add("NOT NULL");
        //columnParameters.add("");
        return columnParameters;
    }

    private static Vector<String> getProteinValues(Protein protein) {
        Vector<String> proteinValues = new Vector<>();
        proteinValues.add(protein.getProteinSequenceAsString());
        proteinValues.add(protein.getName());
        proteinValues.add(protein.getFunctions());
        proteinValues.add(Double.toString(protein.getMolecularWeight()));
        proteinValues.add(Double.toString(protein.getpI()));
        //proteinValues.add(protein.getProperty());
        return proteinValues;
    }

    private static Protein createProteinfromResultSet(ResultSet proteinResultSet) {
        Vector<String> columnNames = getColumnNames();
        Protein protein = null;
        try {
            String sequence = proteinResultSet.getString(columnNames.get(0));
            String name = proteinResultSet.getString(columnNames.get(1));
            String functions = proteinResultSet.getString(columnNames.get(2));
            double molecularWeight = proteinResultSet.getDouble(columnNames.get(3));
            double pI = proteinResultSet.getDouble(columnNames.get(4));
            protein = new Protein(sequence, name, functions, molecularWeight, pI);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return protein;
    }

}
