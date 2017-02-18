package org.jbioframework.library.utilities;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Configuration {

    final Logger logger = LoggerFactory.getLogger(Configuration.class);

    Map<String, Object> configParameters;

    public Configuration() {
        configParameters = ConfigurationConstants.getConfigurationDefaultMap();
    }

    public Object getProperty(String field) {
        for (String configField : configParameters.keySet()) {
            if (configField == field) {
                return configParameters.get(field);
            }
        }
        logger.error("Could not find configuration field "+field+"!");
        return null;
    }

}
