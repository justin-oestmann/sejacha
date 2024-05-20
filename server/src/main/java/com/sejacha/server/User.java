
package com.sejacha.server;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class User {

    private String id;
    private String name;
    private String email;
    private String password;
    private LocalDateTime password_changed;
    private Boolean state;
    private LocalDateTime created_at;
    private LocalDateTime updated_at;
    private Boolean auth;
    private String authKey;

    public User() {

    }

    public boolean login(String email, String password) {

        try {
            PreparedStatement statement = Database.getConnection()
                    .prepareStatement("SELECT * FROM users WHERE user_email =? AND user_password =?");
            statement.setString(1, email);
            statement.setString(2, password);
            ResultSet result = statement.executeQuery();

            if (result.next() && result.getString("user_email") == email
                    && result.getString("user_password") == password) {
                this.id = result.getString("id");
                this.name = result.getString("name");
                this.email = result.getString("email");
                this.password = result.getString("password");
                this.state = result.getBoolean("state");
                this.auth = true;
                return true;
            } else {
                this.auth = false;
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;

    }

    public boolean login(String loginToken) {
        SysPrinter.println("Baum", "NIX DA FIGG DICH");
        return false;
    }

    public boolean register() {
        return true;
    }

    private String generateAuthKey() throws Exception {
        if (this.authKey != null) {
            throw new Exception("Authkey already generated!");
        }
        return this.authKey = RandomString.generate(32);
    }

    private boolean checkAuthKey(String authKey) {
        return this.authKey.equals(authKey);
    }

}
