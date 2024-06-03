/**
 * This class represents a User in the system with various attributes and methods 
 * to manage user data, including loading from the database, saving, creating new users,
 * and handling verification processes.
 */
package com.sejacha.server;

// import java.sql.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.sql.Timestamp;

import javax.mail.MessagingException;

import com.sejacha.server.exceptions.MissingParameterException;
import com.sejacha.server.exceptions.UserInvalidStateException;
import com.sejacha.server.exceptions.UserNoVerifyCodeExists;

public class User {

    private String id = null;
    private String name;
    private String email;

    private String password;
    private LocalDateTime password_changed_at;

    private UserState state = UserState.UNVERIFIED;

    private LocalDateTime user_updated_at;

    private String verify_code;
    private LocalDateTime verified_at = null;

    private String authKey = null; // ONLY LOCAL!!!

    public User() {
    }

    /**
     * Constructs a User object and loads user details from the database based on
     * the given ID.
     * 
     * @param id the ID of the user
     */
    public User(String id) {
        this.loadByID(id);
    }

    /**
     * Loads the user details from the database based on the given user ID.
     * 
     * @param id the ID of the user
     * @return true if the user is successfully loaded, false otherwise
     */
    public boolean loadByID(String id) {
        try {
            PreparedStatement statement = Database.getConnection().prepareStatement(
                    "SELECT * FROM users WHERE user_id = ?", ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);
            statement.setString(1, id);

            ResultSet result = statement.executeQuery();

            if (!result.next()) {
                return false;
            }
            result.beforeFirst();

            while (result.next()) {
                this.id = result.getString("user_id");
                this.name = result.getString("user_name");
                this.email = result.getString("user_email");
                this.password = result.getString("user_password");
                this.password_changed_at = result.getTimestamp("user_password_changed_at").toLocalDateTime();
                this.state = UserState.fromInt(result.getInt("user_state"));
                this.user_updated_at = result.getTimestamp("user_updated_at").toLocalDateTime();
                this.verify_code = result.getString("user_email_verify_code");
                this.verified_at = result.getTimestamp("user_email_verified_at").toLocalDateTime();
            }
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Loads the user details from the database based on the given email.
     * 
     * @param email the email of the user
     * @return true if the user is successfully loaded, false otherwise
     */
    public boolean loadByEmail(String email) {
        try {
            PreparedStatement statement = Database.getConnection().prepareStatement(
                    "SELECT * FROM users WHERE user_email = ?", ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);
            statement.setString(1, email);

            ResultSet result = statement.executeQuery();

            if (!result.next()) {
                return false;
            }
            result.beforeFirst();

            while (result.next()) {
                this.id = result.getString("user_id");
                this.name = result.getString("user_name");
                this.email = result.getString("user_email");
                this.password = result.getString("user_password");
                this.password_changed_at = result.getTimestamp("user_password_changed_at").toLocalDateTime();
                this.state = UserState.fromInt(result.getInt("user_state"));
                this.user_updated_at = result.getTimestamp("user_updated_at").toLocalDateTime();
                this.verify_code = result.getString("user_email_verify_code");
                try {
                    this.verified_at = result.getTimestamp("user_email_verified_at").toLocalDateTime();
                } catch (Exception ex) {
                    this.verified_at = null;
                }
            }

            return true;

        } catch (

        SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Saves the current state of the user to the database.
     * 
     * @return true if the user is successfully saved, false otherwise
     */
    public boolean save() {
        try {
            PreparedStatement statement = Database.getConnection().prepareStatement(
                    "UPDATE users SET user_name = ?, user_email = ?, user_password = ?, user_password_changed_at = ?, user_state = ?, user_updated_at = ?, user_email_verify_code = ?, user_email_verified_at = ? WHERE user_id = ?");
            statement.setString(1, this.name);
            statement.setString(2, this.email);
            statement.setString(3, this.password);
            statement.setTimestamp(4, Timestamp.valueOf(this.password_changed_at));
            statement.setInt(5, this.state.getNameOfType());
            statement.setTimestamp(6, Timestamp.valueOf(this.user_updated_at));
            statement.setString(7, this.verify_code);
            statement.setTimestamp(8, Timestamp.valueOf(this.verified_at));
            statement.setString(9, this.id);

            int affectedRows = statement.executeUpdate();
            return affectedRows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Creates a new user in the database with the current attributes.
     * 
     * @return true if the user is successfully created, false otherwise
     * @throws MissingParameterException if any required parameter is missing
     */
    public boolean create() throws MissingParameterException {
        if (id == null)
            throw new MissingParameterException("id");
        if (name == null)
            throw new MissingParameterException("name");
        if (email == null)
            throw new MissingParameterException("email");
        if (password == null)
            throw new MissingParameterException("password");
        if (password_changed_at == null)
            throw new MissingParameterException("password_changed_at");
        if (state == null)
            throw new MissingParameterException("state");
        if (user_updated_at == null)
            throw new MissingParameterException("user_updated_at");
        if (verify_code == null)
            throw new MissingParameterException("verify_code");

        try {
            Connection connection = Database.getConnection();
            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO users (user_id, user_name, user_email, user_password, user_password_changed_at, user_state, user_updated_at, user_email_verify_code, user_email_verified_at) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)");

            statement.setString(1, this.id);
            statement.setString(2, this.name);
            statement.setString(3, this.email);
            statement.setString(4, this.password);
            statement.setTimestamp(5, Timestamp.valueOf(this.password_changed_at));
            statement.setInt(6, this.state.getNameOfType());
            statement.setTimestamp(7, Timestamp.valueOf(this.user_updated_at));
            statement.setString(8, this.verify_code);
            if (this.verified_at == null) {
                statement.setTimestamp(9, null);
            } else {
                statement.setTimestamp(9, Timestamp.valueOf(this.verified_at));
            }

            int affectedRows = statement.executeUpdate();
            return affectedRows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // /**
    // * Validates the user's login credentials.
    // *
    // * @param email the email of the user
    // * @param password the password of the user
    // * @return true if the credentials are correct, false otherwise
    // */
    // public boolean login(String email, String password) {
    // return email.equals(this.email) && password.equals(this.password);
    // }

    /**
     * Gets the ID of the user.
     * 
     * @return the user's ID
     */
    public String getID() {
        return this.id;
    }

    /**
     * Sets the ID of the user.
     * 
     * @param id the new ID
     */
    @SuppressWarnings("unused")
    private void setID(String id) {
        this.id = id;
        this.user_updated_at = LocalDateTime.now();
    }

    public void genID() throws Exception {
        if (this.id != null) {
            throw new Exception("user is already loaded");
        }
        this.id = Database.getUniqueID("user");
        this.user_updated_at = LocalDateTime.now();
    }

    /**
     * Gets the name of the user.
     * 
     * @return the user's name
     */
    public String getName() {
        return this.name;
    }

    /**
     * Sets the name of the user.
     * 
     * @param name the new name
     */
    public void setName(String name) {
        this.name = name;
        this.user_updated_at = LocalDateTime.now();
    }

    /**
     * Gets the email of the user.
     * 
     * @return the user's email
     */
    public String getEmail() {
        return this.email;
    }

    /**
     * Sets the email of the user.
     * 
     * @param email the new email
     */
    public void setEmail(String email) {
        this.email = email;
        this.user_updated_at = LocalDateTime.now();
    }

    /**
     * Gets the hashed password of the user.
     * 
     * @return the user's hashed password
     */
    @SuppressWarnings("unused")
    private String getPasswordHash() {
        return this.password;
    }

    /**
     * Sets the hashed password of the user.
     * 
     * @param password_hash the new hashed password
     */
    public void setPasswordHash(String password_hash) {
        this.password = password_hash;
        this.password_changed_at = LocalDateTime.now();
    }

    /**
     * Gets the time when the password was last changed.
     * 
     * @return the time of the last password change
     */
    public LocalDateTime getPasswordChangedAt() {
        return this.password_changed_at;
    }

    /**
     * Sends a verification code to the user's email.
     * 
     * @throws UserInvalidStateException if the user is not in the UNVERIFIED state
     * @throws MessagingException        if there is an error sending the email
     */
    public void sendVerificationCode() throws UserInvalidStateException, MessagingException {
        if (this.state != UserState.UNVERIFIED) {
            throw new UserInvalidStateException("user is not in the right state!");
        }

        this.verify_code = RandomString.generateNumberCode(6);
        Mailing.sendEmail(this.getEmail(), Language.getText(LanguageText.EMAIL_VERIFY_SUBJECT), this.verify_code);
    }

    /**
     * Verifies the user's account with the given code.
     * 
     * @param codeString the verification code
     * @return true if the account is successfully verified, false otherwise
     * @throws UserInvalidStateException if the user is not in the UNVERIFIED state
     */
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

    /**
     * Verifies the given authentication key.
     * 
     * @param codeString the authentication key
     * @return true if the key is correct, false otherwise
     * @throws UserNoVerifyCodeExists if the authentication key does not exist
     */
    public boolean verifyAuthKey(String codeString) throws UserNoVerifyCodeExists {
        if (this.authKey == null) {
            throw new UserNoVerifyCodeExists("authkey is null");
        }

        if (this.authKey.equals(codeString)) {
            return true;
        }

        return false;
    }

    /**
     * Generates a new authentication key for the user.
     * 
     * @return the new authentication key
     */
    public String generateAuthKey() {
        return this.authKey = RandomString.generate(32);
    }

    public String getAuthKey() {
        return this.authKey;
    }

    public boolean verifyPassword(String passwordHash) {
        return this.password.equals(passwordHash);
    }

    public boolean isVerified() {
        return verified_at == null ? false : true;
    }

}
