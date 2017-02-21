package org.jbioframework.library.utilities;

import com.esotericsoftware.yamlbeans.YamlException;
import com.esotericsoftware.yamlbeans.YamlReader;
import com.esotericsoftware.yamlbeans.YamlWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ConfigurationUtilities {

    private final Logger logger = LoggerFactory.getLogger(ConfigurationUtilities.class);

    private String toAbsolutePath(String relativePath) {
        Path path = Paths.get(relativePath);
        if (path.isAbsolute()) {
            return path.normalize().toString();
        } else {
            return path.normalize().toAbsolutePath().toString();
        }
    }

    private String getConfigFileAbsolutePath() {
        String configFileRelativePath = "./" + ConfigurationConstants.CONFIG_FOLDER + "/" + ConfigurationConstants.CONFIG_FILE_NAME + "." + ConfigurationConstants.CONFIG_FILE_EXTENSION;
        String configFileAbsolutePath = toAbsolutePath(configFileRelativePath);
        return configFileAbsolutePath;
    }

    public Object loadConfigurationSetting(String setting) {
        String configFileAbsolutePath = getConfigFileAbsolutePath();
        if (!doesConfigurationFileExist()) {
            createDefaultConfigurationFile(configFileAbsolutePath);
            logger.info("Created configuration file at " + configFileAbsolutePath + ". \n");
        }
        Configuration config = loadConfigurationFile(configFileAbsolutePath);
        if (config == null) {
            logger.error("Could not load configuration file!");
            return null;
        }
        return config.getProperty(setting);
    }

    private Configuration loadConfigurationFile(String configFileAbsolutePath) {
        Configuration config = null;
        try {
            FileReader configFileReader = new FileReader(configFileAbsolutePath);
            if (configFileReader == null) {
                logger.error("Configuration file not found! \t" +
                        "(" + configFileAbsolutePath + ") \n");
            }
            YamlReader configReader = new YamlReader(configFileReader);
            try {
                config = configReader.read(Configuration.class);

            } catch (YamlException e) {
                logger.error("Error parsing configuration file! \n" + e.getMessage());
            }

        } catch (FileNotFoundException e) {
            logger.error("Configuration file not found! \t" +
                    "(" + configFileAbsolutePath + ") \n");
        }
        return config;
    }

    private void createDefaultConfigurationFile(String configFileAbsolutePath) {
        if (doesConfigurationFileExist()) { return; }
        try {
            FileWriter configFileWriter = new FileWriter(configFileAbsolutePath);
            if (configFileWriter == null) {
                logger.error("Configuration file not found! \t" +
                        "(" + configFileAbsolutePath + ") \n");
                return;
            }
            YamlWriter configWriter = new YamlWriter(configFileWriter);
            try {
                Configuration config = new Configuration();
                configWriter.write(config);
            } catch (YamlException e) {
                logger.error("Error parsing configuration file! \n" + e.getMessage());
            }

        } catch (java.io.IOException e) {
            logger.error("Couldn't make configuration file! \t" +
                    "(" + configFileAbsolutePath + ") \n");
        }
    }

    public boolean doesConfigurationFileExist() {
        boolean fileExists = false;
        File configFile = new File(getConfigFileAbsolutePath());
        if (configFile.exists()) {
            fileExists = true;
        }
        return fileExists;
    }

}
