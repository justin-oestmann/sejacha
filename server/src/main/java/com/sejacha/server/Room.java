package com.sejacha.server;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
// import java.util.List;

import com.sejacha.server.exceptions.MissingParameterException;

public class Room {
    private boolean isLoaded = false;
    private String id = null;
    private String owner = null;
    private String name = null;
    private RoomType type = RoomType.PUBLIC;
    private String password = null;
    private LocalDateTime timestamp;

    public Room() {

    }

    /**
     * Initializes a Room if an ID is not already set
     * 
     * @param owner String owner of the Room
     * @param name  String name of the Room
     * @return true = successful / false = failed
     * @throws Exception
     */
    public boolean initRoom(String owner, String name) throws Exception {
        if (this.id != null) {
            return false;
        }

        this.id = Database.getUniqueID("room");
        this.owner = owner;
        this.name = name;

        return true;
    }

    /**
     * Initializes a Room if an ID is not already set
     * 
     * @param owner String owner of the Room
     * @param name  String name of the Room
     * @param type  int declares the type of the Room (PUBLIC / PRIVATE / BROADCAST)
     * @return true = successful / false = failed
     * @throws Exception
     */
    public boolean initRoom(String owner, String name, RoomType type) throws Exception {
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
     * Initializes a private Room if an ID is not already set
     * 
     * @param owner    String owner of the Room
     * @param name     String name of the Room
     * @param password String password of the private Room
     * @return true = successful / false = failed
     * @throws Exception
     */
    public boolean initRoom(String owner, String name, String password) throws Exception {
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

    /**
     * Inserts the Room Object in the Database if an id is already set
     * 
     * @return true = successful / false = failed
     */
    public boolean create() throws MissingParameterException {
        if (id == null)
            throw new MissingParameterException("id");
        if (owner == null)
            throw new MissingParameterException("owner");
        if (name == null)
            throw new MissingParameterException("name");
        if (type == null)
            throw new MissingParameterException("type");
        if (type.getNameOfType() == 1 && password == null)
            throw new MissingParameterException("password");

        if (this.isLoaded) {
            return false;
        }

        try {
            PreparedStatement statement = Database.getConnection().prepareStatement(
                    "INSERT INTO room (room_id, room_owner, room_name, room_type, room_password) VALUES (?, ?, ?, ?, ?)");
            statement.setString(1, this.id);
            statement.setString(2, this.owner);
            statement.setString(3, this.name);
            statement.setInt(4, this.type.getNameOfType());
            statement.setString(5, this.password);

            ResultSet result = statement.executeQuery();
            return result.rowInserted();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Updates the Room Object in the Database
     * 
     * @return true = successful / false = failed
     */
    public boolean save() {
        if (this.id == null) {
            return false;
        }

        try {
            PreparedStatement statement = Database.getConnection().prepareStatement(
                    "UPDATE room SET room_owner=?, room_name=?, room_type=?, room_password=? WHERE room_id=?");
            statement.setString(1, this.owner);
            statement.setString(2, this.name);
            statement.setInt(3, this.type.getNameOfType());
            statement.setString(4, this.password);
            statement.setString(5, this.id);

            ResultSet result = statement.executeQuery();
            return result.rowUpdated();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Loads the Parameters of the Room Object, that are saved in the Database
     * 
     * @return true = successful / false = failed
     */
    public boolean loadByID(String idString) {
        try {
            PreparedStatement statement = Database.getConnection().prepareStatement(
                    "SELECT * FROM rooms WHERE room_id=? LIMIT 0,1", ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);
            statement.setString(1, idString);

            ResultSet result = statement.executeQuery();

            if (!result.next()) {
                return false;
            }
            result.beforeFirst();

            while (result.next()) {
                this.id = result.getString("room_id");
                this.owner = result.getString("room_owner");
                this.name = result.getString("room_name");
                this.type = RoomType.fromInt(result.getInt("room_type"));
                this.password = result.getString("room_password");
            }

            this.isLoaded = true;

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
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
