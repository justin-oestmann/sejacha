package com.sejacha.server;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Config {

    public static String getConfig(String v) {
        Properties properties = new Properties();
        String propertiesFileName = "config.properties";

        try (InputStream inputStream = Config.class.getClassLoader().getResourceAsStream(propertiesFileName)) {
            if (inputStream != null) {
                properties.load(inputStream);
                // Eigenschaften verwenden
                return properties.getProperty(v);
            } else {
                SysPrinter.println("Config", "Property file '" + propertiesFileName + "' not found in the classpath");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }
}
