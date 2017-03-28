package org.jbioframework.library.utilities;

import org.jbioframework.library.protein.Protein;

import java.io.File;

import java.sql.*;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class DatabaseUtilities {

    private final static Logger logger = LoggerFactory.getLogger(DatabaseUtilities.class);

    public static void deleteDatabaseFile(String fileName) {
        String path = getDatabaseRelativePath(fileName);
        File databaseFile = new File(path);
        databaseFile.delete();

    }

    private static void checkDatabaseFolderExists() {
        String filePath = getDatabaseRelativePath("");
        String folderPath = processRelativePath(filePath.substring(0,filePath.indexOf(".db")-1));
        File databaseFolder = new File(folderPath);
        if (!(databaseFolder.exists())) {
            if (!(databaseFolder.mkdir())) {
                logger.error("Could not make database folder ("+databaseFolder.getAbsolutePath()+")!");
            }
        }

    }

    public static ArrayList<Protein> loadDatabase(String filename) {
        checkDatabaseFolderExists();
        ArrayList<Protein> proteins = new ArrayList<>();
        try {
            Connection databaseConnection = connect(getDatabaseRelativePath(filename));
            Statement sqlStatement = databaseConnection.createStatement();
            String sqlCommand = constructSQLProteinRetrieveAllCommand(filename);
            ResultSet proteinResultSet = sqlStatement.executeQuery(sqlCommand);
            while (proteinResultSet.next()) {
                proteins.add(createProteinfromResultSet(proteinResultSet));
            }
            sqlStatement.close();
            databaseConnection.close();
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return proteins;
    }

    public static void saveDatabase(ArrayList<Protein> proteins, String fileName) {
        checkDatabaseFolderExists();
        try {
            Connection databaseConnection = connect(getDatabaseRelativePath(fileName));
            Statement sqlStatement = databaseConnection.createStatement();
            String totalSqlCommand = "";
            String sqlCreateTableCommand = constructSQLTableCreationCommand(fileName,getColumnNames(),getColumnParameters(),getcolumnTypes());
            totalSqlCommand+=sqlCreateTableCommand;


            databaseConnection.setAutoCommit(false);
            for (Protein protein : proteins) {
                String tableName = fileName;
                String sqlInsertCommand = constructSQLProteinCreationCommand(tableName, getColumnNames(), getColumnParameters(), getcolumnTypes(), protein);
                totalSqlCommand+=sqlInsertCommand;
            }
            sqlStatement.execute(totalSqlCommand);
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

    private static String constructSQLTableCreationCommand(String tableName, ArrayList<String> columnNames,
                                                           ArrayList<String> columnParameters, ArrayList<String> columnTypes) {
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

    private static String constructSQLProteinCreationCommand(String tableName, ArrayList<String> columnNames,
                                                            ArrayList<String> columnParameters, ArrayList<String> columnTypes,
                                                            Protein protein) {
        String sqlCommand = "INSERT INTO "+tableName+"(";
        for (String columnName : columnNames) {
            sqlCommand+=columnName;
            if (!(columnName == columnNames.get(columnNames.size() - 1))) {
                sqlCommand+=",";
            }
        }
        sqlCommand+=") VALUES(";
        ArrayList<String> proteinValues = getProteinValues(protein);
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

    private static String constructSQLProteinRemovalCommand(String tableName, ArrayList<String> columnNames,
                                                             ArrayList<String> columnParameters, ArrayList<String> columnTypes,
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

    private static String constructSQLProteinRetrieveAllCommand(String tableName) {
        String sqlCommand = "";
        sqlCommand+="SELECT * FROM "+tableName+";";
        return sqlCommand;
    }

    private static String constructSQLProteinRetrieveSpecificCommand(String tableName, ArrayList<String> columnNames,
                                                                ArrayList<String> columnParameters, ArrayList<String> columnTypes,
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

    private static int findPrimaryColumnIndex(ArrayList<String> columnParameters, String tableName) {
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
        return processRelativePath((new Configuration().getProperty(ConfigurationConstants.DATABASE_FOLDER))+fileName+".db");
    }

    private static String processRelativePath(String relativePath) {
        String processedRelativePath = relativePath;
        if (relativePath.contains("//")) {
            if ((relativePath.indexOf("//") + 2) > (relativePath.length() - 1) ){
                processedRelativePath = relativePath.substring(0,relativePath.indexOf("//"));
            } else {
                processedRelativePath = relativePath.substring(0,relativePath.indexOf("//")) + relativePath.substring(relativePath.indexOf("//")+2,relativePath.length()-1);
            }
        }
        return processedRelativePath;
    }

    private static ArrayList<String> getColumnNames() {
        ArrayList<String> columnNames = new ArrayList<>();
        columnNames.add("sequence");
        columnNames.add("name");
        columnNames.add("functions");
        columnNames.add("molecular_weight");
        columnNames.add("isoelectric_point");
        //columnNames.add("");
        return columnNames;
    }

    private static ArrayList<String> getcolumnTypes() {
        ArrayList<String> columnTypes = new ArrayList<>();
        columnTypes.add("text");
        columnTypes.add("text");
        columnTypes.add("text");
        columnTypes.add("real");
        columnTypes.add("real");
        //columnTypes.add("");
        return columnTypes;
    }

    private static ArrayList<String> getColumnParameters() {
        ArrayList<String> columnParameters = new ArrayList<>();
        columnParameters.add("NOT NULL");
        columnParameters.add("");
        columnParameters.add("");
        columnParameters.add("NOT NULL");
        columnParameters.add("NOT NULL");
        //columnParameters.add("");
        return columnParameters;
    }

    private static ArrayList<String> getProteinValues(Protein protein) {
        ArrayList<String> proteinValues = new ArrayList<>();
        proteinValues.add(protein.getProteinSequenceAsString());
        proteinValues.add(protein.getName());
        proteinValues.add(protein.getFunctions());
        proteinValues.add(Double.toString(protein.getMolecularWeight()));
        proteinValues.add(Double.toString(protein.getpI()));
        //proteinValues.add(protein.getProperty());
        return proteinValues;
    }

    private static Protein createProteinfromResultSet(ResultSet proteinResultSet) {
        ArrayList<String> columnNames = getColumnNames();
        Protein protein = null;
        try {
            String sequence = proteinResultSet.getString(columnNames.get(0));
            String name = proteinResultSet.getString(columnNames.get(1));
            String functions = proteinResultSet.getString(columnNames.get(2));
            double molecularWeight = proteinResultSet.getDouble(columnNames.get(3));
            double pI = proteinResultSet.getDouble(columnNames.get(4));
            protein = new Protein(sequence, name, functions, molecularWeight, pI);
            //logger.info("Created protein at "+(DateTimeFormatter.ofPattern("HH:mm:ss").format(LocalDateTime.now())));
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return protein;
    }

}
