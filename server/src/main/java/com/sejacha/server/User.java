
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

    public User() {

    }

    public boolean login(String email, String password) {

    }

    public boolean login(String loginToken) {

    }

    public boolean register() {

    }

}
