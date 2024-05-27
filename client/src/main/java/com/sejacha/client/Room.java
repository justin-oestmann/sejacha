package com.sejacha.client;

import java.time.LocalDateTime;

public class Room {
    private int id;
    private String name;
    private String owner;
    private int type;
    private String password;
    private LocalDateTime timestamp;

    public Room(int id, String name, String owner, int type, String password, LocalDateTime timestamp) {
        this.id = id;
        this.name = name;
        this.owner = owner;
        this.type = type;
        this.password = password;
        this.timestamp = timestamp;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getOwner() {
        return owner;
    }

    public int getType() {
        return type;
    }

    public String getPassword() {
        return password;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }
}
