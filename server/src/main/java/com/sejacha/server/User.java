
package com.sejacha.server;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;

public class User {

    private String id;
    private String name;
    private String email;
    private String password;
    private Boolean state;
    private Boolean auth;

    public boolean auth(String username, String passwordHash) throws Exception {

        String query = "SELECT * FROM users WHERE user_name = ? AND user_password = ?";
        PreparedStatement stmt = Database.getConnection().prepareStatement(query);

        stmt.setString(1, username);
        stmt.setString(2, passwordHash);

        ResultSet rs = stmt.executeQuery();

        if (rs.next() && rs.getString("user_name") == username && rs.getString("user_password") == passwordHash) {
            this.auth = true;
            // return true;
        } else {
            // return false;
        }

        // SysPrinter.println("info!", rs.getString("user_name"));
        // SysPrinter.println("info!", rs.getString("user_password"));
        // SysPrinter.println("info!", passwordHash);

        return false;
    }

}
