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

    /**
     * Überprüft, ob ein Wert in einer bestimmten row in einer Tabelle bereits
     * existiert
     * true = Duplikat / false = kein Duplikat
     * 
     * @param value
     * @param table
     * @param row
     * @return
     * @throws Exception
     */
    public boolean checkDuplicate(String value, String table, String row) throws Exception {
        try {
            PreparedStatement statement = Database.getConnection().prepareStatement("SELECT ? FROM ? WHERE ?=?");
            statement.setString(1, row);
            statement.setString(2, table);
            statement.setString(3, row);
            statement.setString(4, value);
            ResultSet result = statement.executeQuery();

            return result.next();

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

    }

    /**
     * 
     * @param DB input "user" or "room" for given id´s
     * @return userID type String
     * @throws Exception
     */
    public static String getUniqueID(String DB) throws Exception {
        boolean isUnique = false;
        String ID = null;

        PreparedStatement ID_check = null;

        try {
            switch (DB) {
                case "user":
                    ID_check = Database.getConnection().prepareStatement(
                            "SELECT user_id FROM users WHERE user_id =?");
                    break;
                case "room":
                    ID_check = Database.getConnection().prepareStatement(
                            "SELECT room_id FROM users WHERE room_id =?");
                    break;
                case "contacts":
                    ID_check = Database.getConnection().prepareStatement(
                            "SELECT contact_id FROM users WHERE contact_id =?");
                    break;

                default:
                    break;
            }

            while (!isUnique) {
                ID = RandomString.generate(8);
                ID_check.setString(1, ID);
                ResultSet ID_check_result = ID_check.executeQuery();
                if (!ID_check_result.next()) {
                    isUnique = true;
                }
            }
            return ID;

        } catch (SQLException e) {
            e.printStackTrace();
            return ID = null; // sollte nie passieren ig und das sollte von db abgefangen werden
        }
    }
}