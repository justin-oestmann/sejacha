package com.sejacha.server;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Config {

    public static String getConfig(String v) {
        Properties properties = new Properties();
        FileInputStream input = null;

        try {
            input = new FileInputStream(
                    "server\\config.properties");
            properties.load(input);

            return properties.getProperty(v);

        } catch (Exception ex) {
            ex.printStackTrace();

        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;

    }
}
