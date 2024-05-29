package com.sejacha.server;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Message {

    private String id;
    private String user_id;
    private String room_id;
    private String text;
    private String attachment;
    private boolean is_read;
    private LocalDateTime timestamp;

    public Message() {

    }

    public Message(String id) {

    }

    /**
     * gets ID
     * 
     * @return id - ID of message
     */
    public String getID() {
        return this.id;
    }

    /**
     * Sets UserID
     * 
     * @param user_id - ID of owner of message
     */
    public void setUserID(String user_id) {
        this.user_id = user_id;
    }

    /**
     * gets UserID
     * 
     * @return user_id - ID of owner of message
     */
    public String getUserID() {
        return this.user_id;
    }

    /**
     * Sets RoomID
     * 
     * @param room_id - ID of room of message
     */
    public void setRoomID(String room_id) {
        this.room_id = room_id;
    }

    /**
     * gets RoomID
     * 
     * @return user_id - ID of room of message
     */
    public String getRoomID() {
        return this.room_id;
    }

    /**
     * Sets Message
     * 
     * @param message - String that contains the Message
     */
    public void setMessage(String message) {
        this.text = message;
    }

    /**
     * gets Message
     * 
     * @return message
     */
    public String getMessage() {
        return this.text;
    }

    /**
     * Sets Date and Time to now
     */
    public void setTimeCreated() {
        this.timestamp = LocalDateTime.now();
    };

    /**
     * Sets Date and Time from Attribute
     * 
     * @param date - LocalDateTime that contains the Date and Time
     */
    public void setTimeCreated(LocalDateTime date) {
        this.timestamp = date;
    }

    /**
     * gets timestamp
     * 
     * @return timestamp
     */
    public LocalDateTime getTimeCreated() {
        return this.timestamp;
    }

    public boolean create() throws Exception {
        if (this.id != null) {
            throw new Exception("id already set! Data may be already exists on database");
        }

        if (this.room_id == null) {
            throw new Exception("room not set!");
        }

        if (this.user_id == null) {
            throw new Exception("user_id not set!");
        }

        if (this.timestamp == null) {
            throw new Exception("timestamp not set!");
        }

        if (this.text == null) {
            throw new Exception("message not set!");
        }

        String query = "INSERT INTO messages (message_id,message_user_id,message_room_id,message_timestamp,message_text) VALUES (?,?,?,?,?)";
        PreparedStatement stmt = Database.getConnection().prepareStatement(query);

        stmt.setString(1, this.id);
        stmt.setString(2, this.user_id);
        stmt.setString(3, this.room_id);
        stmt.setString(4, this.timestamp.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        stmt.setString(5, this.text);

        ResultSet rs = stmt.executeQuery();
        return rs.rowInserted();
    }

}
