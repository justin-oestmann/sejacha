package com.sejacha.server;

import java.time.LocalDateTime;

public class Message {

    private String id;
    private String user_id;
    private String room_id;
    private String message;
    private LocalDateTime time_created;

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
        this.message = message;
    }

    /**
     * gets Message
     * 
     * @return message
     */
    public String getMessage() {
        return this.message;
    }

    /**
     * Sets Date and Time to now
     */
    public void setTimeCreated() {
        this.time_created = LocalDateTime.now();
    };

    /**
     * Sets Date and Time from Attribute
     * 
     * @param date - LocalDateTime that contains the Date and Time
     */
    public void setTimeCreated(LocalDateTime date) {
        this.time_created = date;
    }

    /**
     * gets time_created
     * 
     * @return time_created
     */
    public LocalDateTime getTimeCreated() {
        return this.time_created;
    }

}
