package com.sejacha.server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
                SysPrinter.println("MYSQL-Database", "Connected to database!");
            } catch (SQLException e) {
                SysPrinter.println("MYSQL-Database", "Error while connecting to database: " + e.getMessage());
            }
        }
        return connection;
    }

    // Statische Methode zum Schließen der Datenbankverbindung
    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                SysPrinter.println("MYSQL-Database", "Disconnected from database");
            } catch (SQLException e) {
                SysPrinter.println("MYSQL-Database",
                        "Error while closing connection to database: " + e.getMessage());
            } finally {
                connection = null; // Setze die Connection-Referenz zurück
            }
        }
    }


    public static String getUniqueID() {
        boolean isUnique = false;
        String userID = null;

        try {
        PreparedStatement uid_check = Database.getConnection().prepareStatement(
                        "SELECT user_id FROM users WHERE user_id =?");

        while (!isUnique) {
                // neue uid zuweisen und checken ob existiert
                userID = RandomString.generate(11);
                uid_check.setString(1, userID);
                ResultSet uid_check_result = uid_check.executeQuery();
                if (!uid_check_result.next()) {
                    isUnique = true;
                }
            }
            return userID;

        } catch (SQLException e) {
            e.printStackTrace();
            return userID = null; //sollte nie passieren ig und das sollte von db abgefangen werden 
        }
    }
}