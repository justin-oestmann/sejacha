
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
    private String password_update;
    private String password_changed_at;
    private int state;
    private LocalDateTime updated_at;
    private Boolean auth;
    private String authKey;

    private String email_verify_code;
    private LocalDateTime email_verified_at;
    private boolean email_verified;

    public User() {
        this.auth = false;

    }

    // muss getestet werden
    public boolean login(String email, String password) throws Exception {
        try (PreparedStatement statement = Database.getConnection().prepareStatement(
                "SELECT * FROM users WHERE user_email =? AND user_password =?")) {
            statement.setString(1, email);
            statement.setString(2, password);
            try (ResultSet result = statement.executeQuery()) {
                if (result.next()
                        && email.equals(result.getString("user_email"))
                        && password.equals(result.getString("user_password"))) {
                    this.id = result.getString("user_id");
                    this.name = result.getString("user_name");
                    this.email = result.getString("user_email");
                    this.password = result.getString("user_password");
                    this.state = result.getInt("user_state");
                    if (this.state != 1) {
                        this.auth = false;
                        throw new Exception("Invalid user state");
                    }
                    this.auth = true;
                    return true;
                }
                this.auth = false;
                return false;

            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // muss getestet werden + nicht fertig
    public boolean login(String loginToken) {
        if (checkAuthKey(loginToken)) {
            this.auth = true;
            return true;
        }
        this.auth = false;
        return false;

    }

    // muss getestet werden
    /**
     * test
     */
    public boolean register() throws Exception {
        try (PreparedStatement mail_name_check = Database.getConnection().prepareStatement(
                "SELECT * FROM users WHERE user_email =? OR user_name =?");
                PreparedStatement insert_Statement = Database.getConnection().prepareStatement(
                        "INSERT INTO users (user_id, user_name, user_email, user_password) VALUES (?,?,?,?)")) {

            mail_name_check.setString(1, email);
            mail_name_check.setString(2, name);
            ResultSet result = mail_name_check.executeQuery();

            id = Database.getUniqueID(); 

            if (!result.next()) {
                insert_Statement.setString(1, id);
                insert_Statement.setString(2, name);
                insert_Statement.setString(3, email);
                insert_Statement.setString(4, password);
                // state muss nicht auf != 1 gesetzt werden da es automatisch in der db
                // passiert.
                insert_Statement.executeUpdate();
                return true;
            }
            throw new Exception("Email or Username already exists");
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // muss getestet werden
    private String generateAuthKey() throws Exception {
        if (this.authKey != null) {
            throw new Exception("Authkey already generated!");
        }
        return this.authKey = RandomString.generate(32);
    }

    // muss getestet werden
    private boolean checkAuthKey(String authKey) {
        // return this.authKey.equals(authKey);
        return this.authKey != null && this.authKey.equals(authKey);
    }

    // muss getestet werden
    private boolean passwordUpdate(String email, String password) throws Exception {
        // email und password checken
        try (PreparedStatement check_pw = Database.getConnection().prepareStatement(
                "SELECT * FROM users WHERE user_email =? AND user_password =?");
                PreparedStatement update_pw = Database.getConnection().prepareStatement(
                        "UPDATE users SET user_password =?, user_password_changed =? WHERE user_email =?")) {

            check_pw.setString(1, email);
            check_pw.setString(2, password);
            ResultSet result = check_pw.executeQuery();

            if (result.next()) {
                password_changed_at = LocalDateTime.now().toString();
                update_pw.setString(1, password);
                update_pw.setString(2, password_changed_at);
                update_pw.setString(3, email);
                update_pw.executeUpdate();
                return true;
            }

            return false;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private String sendEmailVerificationCode() throws Exception {
        if (this.email != null && !this.email.isEmpty()) {
            throw new Exception("No email address set!");
        }
        if (this.email_verified) {
            throw new Exception("Email-Address already verified!");
        }

        try {
            this.email_verify_code = RandomString.generateNumberCode(6);
            Mailing.sendEmail(this.email, "Verification Code for your SEJACHA-Account",
                    "Your verification Code: " + this.email_verify_code);
            return this.email_verify_code;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    private boolean verifyEmailAddress(String code) throws Exception {
        if (this.email_verified) {
            throw new Exception("Email-Address already verified!");
        }

        if (this.email_verify_code.equals(code)) {
            this.email_verified_at = LocalDateTime.now();
            return true;
        }

        throw new Exception("InvalidVerifyToken");
    }
}
