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
     * @throws SQLException if a database access error occurs
     */
    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            String url = "jdbc:mysql://" + Config.getConfig("mysql.server") + ":" + Config.getConfig("mysql.port") + "/"
                    + Config.getConfig("mysql.database");

            try {
                // Establish the database connection
                connection = DriverManager.getConnection(url, Config.getConfig("mysql.user"),
                        Config.getConfig("mysql.password"));
                SysPrinter.println("MYSQL-Database", "Connected to database!");
            } catch (SQLException e) {
                SysPrinter.println("MYSQL-Database", "Error while connecting to database: " + e.getMessage());
                throw e;
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
     * @throws SQLException if a database access error occurs
     */
    public boolean checkDuplicate(String value, String table, String row) throws SQLException {
        try {
            try (PreparedStatement statement = Database.getConnection()
                    .prepareStatement("SELECT * FROM " + table + " WHERE " + row + "=?")) {
                statement.setString(1, value);
                try (ResultSet result = statement.executeQuery()) {
                    return result.next();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    // public boolean checkDuplicate(String value, String table, String row) throws SQLException {
    //     // Validate table and row names to prevent SQL Injection
    //     if (!isValidIdentifier(table) || !isValidIdentifier(row)) {
    //         throw new IllegalArgumentException("Invalid table or row identifier");
    //     }

    //     String query = "SELECT 1 FROM " + table + " WHERE " + row + " = ?";

    //     try (Connection connection = Database.getConnection();
    //             PreparedStatement statement = connection.prepareStatement(query)) {

    //         statement.setString(1, value);

    //         try (ResultSet result = statement.executeQuery()) {
    //             return result.next();
    //         }
    //     } catch (SQLException e) {
    //         e.printStackTrace();
    //         throw e;
    //     }
    // }

    // private boolean isValidIdentifier(String identifier) {
    //     // Add your validation logic here (e.g., regex to check for valid SQL
    //     // identifiers)
    //     return identifier != null && identifier.matches("[a-zA-Z_][a-zA-Z0-9_]*");
    // }

    /**
     * Generates a unique ID for the specified database entity.
     * 
     * @param entity the database entity ("user", "room", or "contacts")
     * @return a unique ID as a {@code String}
     * @throws SQLException if a database access error occurs
     */
    public static String getUniqueID(String entity) throws SQLException {
        String tableName;
        String idColumn;

        switch (entity) {
            case "user":
                tableName = "users";
                idColumn = "user_id";
                break;
            case "room":
                tableName = "rooms";
                idColumn = "room_id";
                break;
            case "contacts":
                tableName = "contacts";
                idColumn = "contact_id";
                break;
            default:
                throw new IllegalArgumentException("Invalid entity type: " + entity);
        }

        try (PreparedStatement idCheck = Database.getConnection().prepareStatement(
                "SELECT " + idColumn + " FROM " + tableName + " WHERE " + idColumn + " = ?")) {
            String ID;
            ResultSet idCheckResult;
            boolean isUnique;

            do {
                ID = RandomString.generate(8);
                idCheck.setString(1, ID);
                idCheckResult = idCheck.executeQuery();
                isUnique = !idCheckResult.next();
            } while (!isUnique);

            return ID;
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }
}
