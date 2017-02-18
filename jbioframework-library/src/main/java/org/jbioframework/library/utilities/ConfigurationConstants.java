package org.jbioframework.library.utilities;

import java.util.HashMap;
import java.util.Map;

public class ConfigurationConstants {
    public static final String DATA_FOLDER = "dataFolder";
    public static final String DATABASE_CREATION_ENABLED = "databaseCreationEnabled";
    public static final String DATABASE_FOLDER = "databaseFolder";
    public static final String CONFIG_FOLDER = "config";
    public static final String CONFIG_FILE_NAME = "config";
    public static final String CONFIG_FILE_EXTENSION = ".yml";

    public static Map<String, Object> getConfigurationDefaultMap() {
        Map<String, Object> configurationDefaults = new HashMap<>();
        configurationDefaults.put(DATA_FOLDER, "./data/");
        configurationDefaults.put(DATABASE_CREATION_ENABLED, true);
        configurationDefaults.put(DATABASE_FOLDER, "./data/databases/");
        //configurationDefaults.put(FIELD, "default");
        return configurationDefaults;
    }
}
