package com.sejacha.server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {

    private static Connection conn = null;

    public static void createDatabaseConnection() {
        try {
            // Load the MySQL JDBC driver (not necessary for newer JDBC drivers)
            // Class.forName("com.mysql.cj.jdbc.Driver");

            String url = "jdbc:mysql://" + Config.getConfig("mysql.server") + ":" + Config.getConfig("mysql.port") + "/"
                    + Config.getConfig("mysql.database");

            // Establish the database connection
            conn = DriverManager.getConnection(url, Config.getConfig("mysql.user"),
                    Config.getConfig("mysql.password"));

            System.out.println("Verbindung zur Datenbank hergestellt.");

        } catch (SQLException e) {
            System.out.println("Verbindung zur Datenbank fehlgeschlagen!");
            e.printStackTrace();
        }
    }

    public static Connection getInstance() {
        if (conn == null) {
            createDatabaseConnection(); // Establish connection if not already done
        }
        return conn;
    }

    public static void closeConnection() {
        if (conn != null) {
            try {
                conn.close();
                System.out.println("Datenbankverbindung geschlossen.");
            } catch (SQLException e) {
                System.out.println("Fehler beim Schließen der Datenbankverbindung!");
                e.printStackTrace();
            }
        }
    }
}
