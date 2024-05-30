package com.sejacha.server;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Die Klasse Language dient zum Laden von Sprachdaten aus einer
 * properties-Datei und zum Abrufen von Texten basierend auf Schlüsseln.
 */
public class Language {

    private Properties languageData = new Properties();

    /**
     * Lädt die Sprachdaten aus der properties-Datei.
     */
    public void load() {
        FileInputStream input = null;
        try {
            input = new FileInputStream(".\\language.properties");
            this.languageData.load(input);
        } catch (IOException ex) {
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

    /**
     * Gibt den Text basierend auf dem gegebenen Schlüssel zurück.
     * 
     * @param v Der Schlüssel des Textes, der abgerufen werden soll.
     * @return Der Text, der dem Schlüssel entspricht, oder null, wenn der Schlüssel
     *         nicht gefunden wurde.
     */
    public static String getText(LanguageText v) {
        Properties properties = new Properties();
        FileInputStream input = null;
        try {
            input = new FileInputStream(".\\language.properties");
            properties.load(input);
            return properties.getProperty(v.toString());
        } catch (IOException ex) {
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
