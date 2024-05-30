/**
 * The {@code Database} class provides methods for connecting to and interacting with the database.
 * It uses a singleton pattern to manage the database connection and provides utility methods for 
 * checking duplicates and generating unique IDs.
 */
package com.sejacha.server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Database {
    private static Connection connection;

    // Private constructor to prevent instantiation
    private Database() {
        // Empty constructor
    }

    /**
     * Returns the database connection. If the connection is not already
     * established,
     * it initializes the connection using the configuration parameters.
     * 
     * @return the database connection
     */
    public static Connection getConnection() {
        if (connection == null) {
            String url = "jdbc:mysql://" + Config.getConfig("mysql.server") + ":" + Config.getConfig("mysql.port") + "/"
                    + Config.getConfig("mysql.database");

            try {
                // Establish the database connection
                connection = DriverManager.getConnection(url, Config.getConfig("mysql.user"),
                        Config.getConfig("mysql.password"));
                SysPrinter.println("MYSQL-Database", "Connected to database!");
            } catch (SQLException e) {
                SysPrinter.println("MYSQL-Database", "Error while connecting to database: " + e.getMessage());
            }
        }
        return connection;
    }

    /**
     * Closes the database connection if it is established.
     */
    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                SysPrinter.println("MYSQL-Database", "Disconnected from database");
            } catch (SQLException e) {
                SysPrinter.println("MYSQL-Database",
                        "Error while closing connection to database: " + e.getMessage());
            } finally {
                connection = null; // Reset the connection reference
            }
        }
    }

    /**
     * Checks if a value already exists in a specified row of a table.
     * 
     * @param value the value to check for duplicates
     * @param table the table to check
     * @param row   the row to check
     * @return true if a duplicate exists, false otherwise
     * @throws Exception if an error occurs during the check
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
     * Generates a unique ID for the specified database entity.
     * 
     * @param DB the database entity ("user", "room", or "contacts")
     * @return a unique ID as a {@code String}
     * @throws Exception if an error occurs during the ID generation
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
            return null; // This should never happen and should be handled by the database
        }
    }
}
