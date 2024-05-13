package com.sejacha.server;

import java.sql.*;
import com.sejacha.server.Database;

class App {
    public static void main(String[] args) throws SQLException {
        System.out.println("starting...");

        System.out.println("Database:" + Config.getConfig("mysql.server"));

        Statement stmt = Database.getConnection().createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM users");

        while (rs.next()) {
            int id = rs.getInt("id");
            String name = rs.getString("name");
            System.out.println("ID: " + id + ", Name: " + name);
        }
    }
}