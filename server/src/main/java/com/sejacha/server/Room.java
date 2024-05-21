package com.sejacha.server;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class Room {
    public String id;
    public String owner;
    public String name;
    public int type = 0;    // 0 - Public (Standard) / 1 - Private
    public String password;
    public LocalDateTime timestamp;



    public boolean createRoom(String owner, String name){

        //room_id wird automatisch erstellt
        //room_owner
        //room_name
        //room_type
        //room_password
        this.id = RandomString.generate(11);
        // prüfen ob schon vergeben

        try {
            PreparedStatement statement = Database.getConnection().prepareStatement("");

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return true;
    }

    public boolean createRoom(String owner, String name, String type){

      

        return true;
    }

    public boolean createRoom(String owner, String name, String type, String password){


        return true;
    }
}
