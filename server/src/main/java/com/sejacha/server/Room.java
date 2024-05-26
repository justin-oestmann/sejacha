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

    public Room (){

    }

    /**
     * Creats a Room if an ID is not already set
     * @param owner String owner of the Room
     * @param name String name of the Room
     * @return boolean true = could be created / false = couldn't be created
     * @throws Exception
     */
    public boolean createRoom(String owner, String name) throws Exception{
        if (!(this.id != null)){
            this.id = Database.getUniqueID("room");
            this.owner = owner;
            this.name = name;

            return true;
        }
        

        return false;
    }

    /**
     * Creats a Room if an ID is not already set
     * @param owner String owner of the Room
     * @param name String name of the Room
     * @param type int declares the type of the Room (0 - Public (Standard) / 1 - Private)
     * @return boolean true = could be created / false = couldn't be created
     * @throws Exception
     */
    public boolean createRoom(String owner, String name, int type) throws Exception{
        if (this.id.equals(null)){
            if (type != 1) {
                this.id = Database.getUniqueID("room");
                this.owner = owner;
                this.name = name;
                this.type = type;
    
                return true;
            }
        }
        

        return false;
    }

    /**
     * Creats a private Room if an ID is not already set
     * @param owner String owner of the Room
     * @param name String name of the Room
     * @param password String password of the private Room
     * @return
     * @throws Exception
     */
    public boolean createRoom(String owner, String name, String password) throws Exception{
        if (!(this.id != null)){
            
            this.id = Database.getUniqueID("room");
            this.owner = owner;
            this.name = name;
            this.type = 1;
            this.password = password;
    
            return true;
        
        }
        

        return false;
    }
}
