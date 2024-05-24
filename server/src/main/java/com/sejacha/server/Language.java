package com.sejacha.server;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Language {

    private Properties languageData = new Properties();;

    public void load() {

        FileInputStream input = null;

        try {
            input = new FileInputStream(
                    ".\\language.properties");
            this.languageData.load(input);

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

    }

    public static String getText(LanguageText v) {
        Properties properties = new Properties();
        FileInputStream input = null;

        try {
            input = new FileInputStream(
                    ".\\language.properties");
            properties.load(input);

            return properties.getProperty(v.toString());

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
