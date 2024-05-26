package com.sejacha.server;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class Room {
    public String id = null;
    public String owner;
    public String name;
    public RoomType type = RoomType.PUBLIC;
    public String password;
    public LocalDateTime timestamp;

    public Room() {

    }

    /**
     * Creats a Room if an ID is not already set
     * 
     * @param owner String owner of the Room
     * @param name  String name of the Room
     * @return boolean true = could be created / false = couldn't be created
     * @throws Exception
     */
    public boolean createRoom(String owner, String name) throws Exception {
        if (this.id != null) {
            return false;
        }

        this.id = Database.getUniqueID("room");
        this.owner = owner;
        this.name = name;

        return true;
    }

    /**
     * Creats a Room if an ID is not already set
     * 
     * @param owner String owner of the Room
     * @param name  String name of the Room
     * @param type  int declares the type of the Room (0 - Public (Standard) / 1 -
     *              Private)
     * @return boolean true = could be created / false = couldn't be created
     * @throws Exception
     */
    public boolean createRoom(String owner, String name, RoomType type) throws Exception {
        if (this.id != null) {
            return false;
        }

        if (type == RoomType.PRIVATE) {
            return false;
        }

        this.id = Database.getUniqueID("room");
        this.owner = owner;
        this.name = name;
        this.type = type;

        return true;
    }

    /**
     * Creats a private Room if an ID is not already set
     * 
     * @param owner    String owner of the Room
     * @param name     String name of the Room
     * @param password String password of the private Room
     * @return
     * @throws Exception
     */
    public boolean createRoom(String owner, String name, String password) throws Exception {
        if (this.id != null) {
            return false;
        }

        this.id = Database.getUniqueID("room");
        this.owner = owner;
        this.name = name;
        this.type = RoomType.PRIVATE;
        this.password = password;

        return true;
    }

    // getter

    public String getID() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getOwner() {
        return owner;
    }

    public boolean validatePassword(String password) {
        return this.password == password;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public RoomType getType() {
        return type;
    }

    // setter

    private void setID(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public void setType(RoomType type) {
        this.type = type;
    }

}
