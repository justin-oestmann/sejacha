
package com.sejacha.server;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

import javax.mail.MessagingException;

import com.sejacha.server.exceptions.UserInvalidStateException;
import com.sejacha.server.exceptions.UserNoVerifyCodeExists;

public class User {

    private String id;
    private String name;
    private String email;

    private String password;
    private LocalDateTime password_changed_at;

    private UserState state;

    private LocalDateTime user_updated_at;

    private String verify_code;
    private LocalDateTime verified_at;

    private String authKey = null; // ONLY LOCAL!!!

    public User() {

    }

    public User(String id) {
        this.loadByID(id);
    }

    public boolean loadByID(String id) {
        try {
            PreparedStatement statement = Database.getConnection().prepareStatement(
                    "SELECT * FROM users WHERE user_id = ?");
            statement.setString(1, id);

            ResultSet result = statement.executeQuery();
            while (result.next()) {
                this.id = result.getString("id");
                this.name = result.getString("name");
                this.email = result.getString("email");
                this.password = result.getString("password");
                this.password_changed_at = result.getTimestamp("password_changed_at").toLocalDateTime();
                this.state = UserState.fromInt(result.getInt("state"));
                this.user_updated_at = result.getTimestamp("user_updated_at").toLocalDateTime();
                this.verify_code = result.getString("verify_code");
                this.verified_at = result.getTimestamp("verified_at").toLocalDateTime();

            }
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean loadByEmail(String email) {
        try {
            PreparedStatement statement = Database.getConnection().prepareStatement(
                    "SELECT * FROM users WHERE user_email = ?");
            statement.setString(1, email);

            ResultSet result = statement.executeQuery();
            while (result.next()) {
                this.id = result.getString("id");
                this.name = result.getString("name");
                this.email = result.getString("email");
                this.password = result.getString("password");
                this.password_changed_at = result.getTimestamp("password_changed_at").toLocalDateTime();
                this.state = UserState.fromInt(result.getInt("state"));
                this.user_updated_at = result.getTimestamp("user_updated_at").toLocalDateTime();
                this.verify_code = result.getString("verify_code");
                this.verified_at = result.getTimestamp("verified_at").toLocalDateTime();
            
            }
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean save() {
        try {
            PreparedStatement statement = Database.getConnection().prepareStatement(
                    "UPDATE users SET user_name = ?, user_email = ?, user_password = ?, user_password_changed_at = ?, user_state = ?, user_updated_at = ?, user_email_verify_code = ?, user_email_verified_at = ?,");
            statement.setString(1, this.name);
            statement.setString(2, this.email);
            statement.setString(3, this.password);
            statement.setTimestamp(4, this.password_changed_at);
            statement.setInt(5, this.state.getNameOfType());
            statement.setTimestamp(6, this.user_updated_at);
            statement.setString(7, this.verify_code);
            statement.setTimestamp(8, this.verified_at);

            ResultSet result = statement.executeQuery();
            return result.rowUpdated();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean create() {
        // TODO: CREATE-FUNCTION
        return false;
    }

    public void login(String email, String password) {
        // TODO: LOGIN FUNCTION
    }

    public String getID() {
        return this.id;
    }

    private void setID(String id) {
        this.id = id;
        this.user_updated_at = LocalDateTime.now();
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
        this.user_updated_at = LocalDateTime.now();
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
        this.user_updated_at = LocalDateTime.now();
    }

    private String getPasswordHash() {
        return this.password;
    }

    public void setPasswordHash(String password_hash) {
        this.password = password_hash;
        this.password_changed_at = LocalDateTime.now();
    }

    public LocalDateTime getPasswordChangedAt() {
        return this.password_changed_at;
    }

    public void sendVerificationCode() throws UserInvalidStateException, MessagingException {
        if (this.state != UserState.UNVERIFIED) {
            throw new UserInvalidStateException("user is not in the right state!");
        }

        this.authKey = RandomString.generateNumberCode(6);
        Mailing.sendEmail(this.getEmail(), Language.getText(LanguageText.EMAIL_VERIFY_SUBJECT), this.authKey);
        return;

    }

    public boolean verifyAccount(String codeString) throws UserInvalidStateException {
        if (this.state != UserState.UNVERIFIED) {
            throw new UserInvalidStateException("user is not in the right state!");
        }

        if (this.verify_code.equals(codeString)) {
            this.state = UserState.VERIFIED;
            this.verified_at = LocalDateTime.now();
            return true;
        }

        return false;
    }

    public boolean verifyAuthKey(String codeString) throws UserNoVerifyCodeExists {
        if (this.authKey == null) {
            throw new UserNoVerifyCodeExists("authkey is null");
        }

        if (this.authKey.equals(codeString)) {
            return true;
        }

        return false;
    }

    public String generateAuthKey() {
        return this.authKey = RandomString.generate(32);
    }

}
