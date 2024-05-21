
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
    private String password_changed;
    private Boolean state;
    private LocalDateTime updated_at;
    private Boolean auth;
    private String authKey;

    public User() {

    }

    public boolean login(String email, String password) {

        try {
            PreparedStatement statement = Database.getConnection().prepareStatement(
                "SELECT * FROM users WHERE user_email =? AND user_password =?");
            statement.setString(1, email);
            statement.setString(2, password);
            ResultSet result = statement.executeQuery();

            if (result.next() 
            && result.getString("user_email") == email
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
            return false;
        }


    }

    public boolean login(String loginToken) {
        SysPrinter.println("Baum", "NIX DA FIGG DICH");
        return false;
    }

    /**
     * 
     */
    public boolean register( ) throws Exception{

        try {
            PreparedStatement mail_name_check = Database.getConnection().prepareStatement(
                    "SELECT * FROM users WHERE user_email =? OR user_name =?");
            mail_name_check.setString(1, email);
            mail_name_check.setString(2, name);
            ResultSet result = mail_name_check.executeQuery();

            boolean testbool = false;
            while (testbool == false) {
                // neue uid zuweisen und checken ob existiert -> als funktion in database
                // asuulagern
                id = RandomString.generate(11);
                PreparedStatement uid_check = Database.getConnection().prepareStatement(
                        "SELECT user_id FROM users WHERE user_id =?");
                uid_check.setString(1, id);
                ResultSet uid_check_result = uid_check.executeQuery();
                 if (uid_check_result.next() == true) {
                    testbool = true;
                 }
            }
            
            try{
            if (result.next() == false) {
                PreparedStatement statement2 = Database.getConnection().prepareStatement(
                    "INSERT INTO users (user_id, user_name, user_email, user_password) VALUES (?,?,?,?)");
                statement2.setString(1,id);    
                statement2.setString(1,name);
                statement2.setString(2,email);
                statement2.setString(3,password);
                statement2.executeUpdate();
                return true;

            }else{
                throw new Exception("Email or Username already exists");
            }

            } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } 

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
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
                password_changed = LocalDateTime.now().toString();
                update_pw.setString(1, password);
                update_pw.setString(2, password_changed);
                update_pw.setString(3, email);
                update_pw.executeUpdate();
                return true;
            } else {
                return false;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
