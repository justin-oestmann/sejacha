package com.sejacha.server;

import java.sql.PreparedStatement;
// import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents a message sent within a room by a user.
 */
public class Message {

    private String id;
    private String user_id;
    private String room_id;
    private String text;
    // private boolean is_read;
    // private String attachment;
    private LocalDateTime timestamp;

    /**
     * Constructs an empty Message object.
     */
    public Message() {

    }

    /**
     * Constructs a Message object with a specified ID.
     *
     * @param id the ID of the message
     */
    public Message(String id) {

    }

    /**
     * Retrieves the ID of the message.
     *
     * @return the ID of the message
     */
    public String getID() {
        return this.id;
    }

    /**
     * Sets the user ID associated with the message.
     *
     * @param user_id the ID of the user who sent the message
     */
    public void setUserID(String user_id) {
        this.user_id = user_id;
    }

    /**
     * Retrieves the user ID associated with the message.
     *
     * @return the ID of the user who sent the message
     */
    public String getUserID() {
        return this.user_id;
    }

    /**
     * Sets the room ID associated with the message.
     *
     * @param room_id the ID of the room where the message was sent
     */
    public void setRoomID(String room_id) {
        this.room_id = room_id;
    }

    /**
     * Retrieves the room ID associated with the message.
     *
     * @return the ID of the room where the message was sent
     */
    public String getRoomID() {
        return this.room_id;
    }

    /**
     * Sets the text content of the message.
     *
     * @param message the text content of the message
     */
    public void setMessage(String message) {
        this.text = message;
    }

    /**
     * Retrieves the text content of the message.
     *
     * @return the text content of the message
     */
    public String getMessage() {
        return this.text;
    }

    /**
     * Sets the creation timestamp of the message to the current date and time.
     */
    public void setTimeCreated() {
        this.timestamp = LocalDateTime.now();
    };

    /**
     * Sets the creation timestamp of the message to the specified date and time.
     *
     * @param date the date and time of message creation
     */
    public void setTimeCreated(LocalDateTime date) {
        this.timestamp = date;
    }

    /**
     * Retrieves the creation timestamp of the message.
     *
     * @return the creation timestamp of the message
     */
    public LocalDateTime getTimeCreated() {
        return this.timestamp;
    }

    /**
     * Inserts the Message object into the database.
     *
     * @return true if insertion was successful, otherwise false
     * @throws Exception if an error occurs during insertion
     */
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

        // if (this.timestamp == null) {
        //     throw new Exception("timestamp not set!");  //  Wird automatisch von der Datenbank gesetzt
        // }

        if (this.text == null) {
            throw new Exception("message not set!");
        }

        String query = "INSERT INTO messages (msg_id, msg_user_id ,msg_room_id ,msg_send, msg_text ) VALUES (UUID(), ?,?,?,?)";
        PreparedStatement stmt = Database.getConnection().prepareStatement(query);

        stmt.setString(1, this.user_id);
        stmt.setString(2, this.room_id);
        stmt.setString(3, this.timestamp.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        stmt.setString(4, this.text);

        int rowsAffected = stmt.executeUpdate();
        return rowsAffected > 0;
    }

}
