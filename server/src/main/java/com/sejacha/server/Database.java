package com.sejacha.server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
    private Connection connection;

    // Konstruktor
    public Database(String host, int port, String databaseName, String username, String password) {
        String url = "jdbc:mysql://" + host + ":" + port + "/" + databaseName;

        try {
            // Verbindung zur Datenbank herstellen
            this.connection = DriverManager.getConnection(url, username, password);
            System.out.println("Verbindung zur MySQL-Datenbank hergestellt");
        } catch (SQLException e) {
            System.err.println("Fehler beim Verbinden mit der Datenbank: " + e.getMessage());
        }
    }

    // Methode zur Rückgabe der Datenbankverbindung
    public Connection getConnection() {
        return this.connection;
    }

    // Methode zur Schließung der Datenbankverbindung
    public void closeConnection() {
        if (this.connection != null) {
            try {
                this.connection.close();
                System.out.println("Verbindung zur MySQL-Datenbank geschlossen");
            } catch (SQLException e) {
                System.err.println("Fehler beim Schließen der Datenbankverbindung: " + e.getMessage());
            }
        }
    }
}