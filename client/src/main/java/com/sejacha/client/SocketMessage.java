package com.sejacha.client;

import org.json.JSONObject;
import org.json.JSONArray;

public class SocketMessage {

    private boolean isLoadedMessage = false;
    private String authKey;
    private SocketMessageType type;
    private JSONObject data;
    private String username;
    private boolean isAdmin;

    public SocketMessage() {

    }

    public SocketMessage(String jsonString) {
        this.importJSONString(jsonString);

    }

    public String toJSONString() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("authkey", this.authKey);
        jsonObject.put("type", this.type.getNameOfType());
        jsonObject.put("data", this.data);
        return jsonObject.toString();
    }

    public void importJSONString(String jsonString) {
        try {
            JSONObject jsonObject = new JSONObject(jsonString);

            this.authKey = jsonObject.getString("authkey");
            this.type = SocketMessageType.fromString(jsonObject.getString("type"));
            this.data = jsonObject.getJSONObject("data");
            this.isLoadedMessage = true;

        } catch (Exception ex) {
            throw ex;
        }
    }

    public String getAuthKey() {
        return this.authKey;
    }

    public SocketMessageType getType() {
        return this.type;
    }

    public void setType(SocketMessageType socketMessageType) {
        if (!this.isLoadedMessage) {
            this.type = socketMessageType;
        }
    }

    public void setAuthKey(String authKey) {
        if (!this.isLoadedMessage) {
            this.authKey = authKey;
        }
    }

    public void setData(JSONObject socketMessageData) {
        this.data = socketMessageData;
    }

    public JSONObject getData() {
        return this.data;
    }

    public String getUsername() {
        return username;
    }

    public boolean isAdmin() {
        return isAdmin;
    }
}
