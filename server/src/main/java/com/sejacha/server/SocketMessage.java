/**
 * The {@code SocketMessage} class represents a message exchanged over a socket connection.
 * It encapsulates message properties such as authentication key, type, and data.
 */
package com.sejacha.server;

import org.json.JSONObject;
import com.sejacha.server.exceptions.SocketMessageIsNotNewException;

public class SocketMessage {

    private boolean isLoadedMessage = false;
    private String authKey = null;
    private SocketMessageType type;
    private JSONObject data = null;

    /**
     * Constructs an empty {@code SocketMessage}.
     */
    public SocketMessage() {
    }

    /**
     * Constructs an {@code SocketMessage}.
     * 
     * @param authkey Authkey of user. if not available set to {@code null}
     * @param type    MessageType of Message
     * @param data    Data of Message. if not available set to {@code null}
     */
    public SocketMessage(String authkey, SocketMessageType type, JSONObject data) {
        this.authKey = null;
        this.data = null;
        this.type = type;
        if (authkey != null) {
            this.authKey = authkey;
        }
        if (data != null) {
            this.data = data;
        }
    }

    /**
     * Constructs a {@code SocketMessage} by importing data from a JSON string.
     *
     * @param jsonString the JSON string representing the socket message
     */
    public SocketMessage(String jsonString) {
        this.importJSONString(jsonString);
    }

    /**
     * Converts the {@code SocketMessage} to a JSON string.
     *
     * @return the JSON string representing the socket message
     */
    public String toJSONString() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("authkey", this.authKey);
        jsonObject.put("type", this.type.getNameOfType());
        jsonObject.put("data", this.data);
        return jsonObject.toString();
    }

    /**
     * Imports data from a JSON string into the {@code SocketMessage}.
     *
     * @param jsonString the JSON string representing the socket message
     */
    public void importJSONString(String jsonString) {
        try {
            JSONObject jsonObject = new JSONObject(jsonString);

            if (jsonObject.has("authkey")) {
                this.authKey = jsonObject.getString("authkey");
            }

            if (jsonObject.has("type")) {
                this.type = SocketMessageType.fromString(jsonObject.getString("type"));
            }

            if (jsonObject.has("data")) {
                this.data = jsonObject.getJSONObject("data");
            }

        } catch (Exception ex) {
            SysPrinter.println(ex);
        }

        this.isLoadedMessage = true;
    }

    /**
     * Gets the authentication key associated with the {@code SocketMessage}.
     *
     * @return the authentication key
     */
    public String getAuthKey() {
        return this.authKey;
    }

    /**
     * Gets the type of the {@code SocketMessage}.
     *
     * @return the message type
     */
    public SocketMessageType getType() {
        return this.type;
    }

    /**
     * Sets the type of the {@code SocketMessage}.
     *
     * @param socketMessageType the message type to set
     * @throws SocketMessageIsNotNewException if the message is already loaded and
     *                                        cannot be changed
     */
    public void setType(SocketMessageType socketMessageType) throws SocketMessageIsNotNewException {
        if (!this.isLoadedMessage) {
            this.type = socketMessageType;
            return;
        }
        throw new SocketMessageIsNotNewException("This Message is a loaded message and can't be changed");
    }

    /**
     * Sets the authentication key of the {@code SocketMessage}.
     *
     * @param authKey the authentication key to set
     * @throws SocketMessageIsNotNewException if the message is already loaded and
     *                                        cannot be changed
     */
    public void setAuthKey(String authKey) throws SocketMessageIsNotNewException {
        if (!this.isLoadedMessage) {
            this.authKey = authKey;
            return;
        }
        throw new SocketMessageIsNotNewException("This Message is a loaded message and can't be changed");
    }

    /**
     * Sets the data of the {@code SocketMessage}.
     *
     * @param socketMessageData the data to set
     * @throws SocketMessageIsNotNewException if the message is already loaded and
     *                                        cannot be changed
     */
    public void setData(JSONObject socketMessageData) throws SocketMessageIsNotNewException {
        if (!this.isLoadedMessage) {
            this.data = socketMessageData;
            return;
        }
        throw new SocketMessageIsNotNewException("This Message is a loaded message and can't be changed");
    }

    /**
     * Gets the data of the {@code SocketMessage}.
     *
     * @return the message data
     */
    public JSONObject getData() {
        return this.data;
    }
}
