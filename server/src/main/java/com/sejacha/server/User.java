
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
    private String authKey;

    public User() {

    }

    public boolean login(String email, String password) {

    }

    public boolean login(String loginToken) {

    }

    public boolean register() {

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
