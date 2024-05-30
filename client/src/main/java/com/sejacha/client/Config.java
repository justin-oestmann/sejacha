package com.sejacha.client;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Config {
    private Properties properties;

    public Config(String configFile) {
        properties = new Properties();
        try {
            properties.load(new FileInputStream(configFile));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getProperty(String key) {
        return properties.getProperty(key);
    }
}