package com.sejacha.server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
    private static Connection connection;

    // Private Konstruktor, um Instanziierung zu verhindern
    private Database() {
        // Leerer Konstruktor
    }

    // Statische Methode zur Rückgabe der Datenbankverbindung
    public static Connection getConnection() {
        if (connection == null) {
            String url = "jdbc:mysql://" + Config.getConfig("mysql.server") + ":" + Config.getConfig("mysql.port") + "/"
                    + Config
                            .getConfig("mysql.database");

            try {
                // Verbindung zur Datenbank herstellen
                connection = DriverManager.getConnection(url, Config.getConfig("mysql.user"),
                        Config.getConfig("mysql.password"));
                System.out.println("Verbindung zur MySQL-Datenbank hergestellt");
            } catch (SQLException e) {
                System.err.println("Fehler beim Verbinden mit der Datenbank: " + e.getMessage());
            }
        }
        return connection;
    }

    // Statische Methode zum Schließen der Datenbankverbindung
    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                System.out.println("Verbindung zur MySQL-Datenbank geschlossen");
            } catch (SQLException e) {
                System.err.println("Fehler beim Schließen der Datenbankverbindung: " + e.getMessage());
            } finally {
                connection = null; // Setze die Connection-Referenz zurück
            }
        }
    }
}