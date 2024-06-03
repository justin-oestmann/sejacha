package com.sejacha.server;

// import java.io.FileInputStream;
// import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Die Klasse Language dient zum Laden von Sprachdaten aus einer
 * properties-Datei und zum Abrufen von Texten basierend auf Schlüsseln.
 */

public class Language {

    protected static Properties properties = new Properties();
    private static String propertiesFileName = "language.properties";

    /**
     * Lädt die Sprachdaten aus der properties-Datei.
     */
    public static void load() {

        try (InputStream inputStream = Config.class.getClassLoader().getResourceAsStream(propertiesFileName)) {
            if (inputStream != null) {
                properties.load(inputStream);
                // Eigenschaften verwenden
                return;
            } else {
                SysPrinter.println("Language", "Property file '" + propertiesFileName + "' not found in the classpath");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return;
    }

    /**
     * Gibt den Text basierend auf dem gegebenen Schlüssel zurück.
     * 
     * @param v Der Schlüssel des Textes, der abgerufen werden soll.
     * @return Der Text, der dem Schlüssel entspricht, oder null, wenn der Schlüssel
     *         nicht gefunden wurde.
     */
    public static String getText(LanguageText v) {
        try {
            return properties.getProperty(v.getNameOfType());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;

    }
}
