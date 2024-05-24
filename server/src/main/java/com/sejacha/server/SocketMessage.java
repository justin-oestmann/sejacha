package com.sejacha.server;

import org.json.JSONObject;
import org.json.JSONArray;

public class SocketMessage {

    private boolean isLoadedMessage = false;
    private String authKey;
    private SocketMessageType type;
    private JSONObject data;

    public SocketMessage() {

    }

    public SocketMessage(String jsonString) {
        this.importJSONString(jsonString);

    }

    public String toJsoString() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("authkey", this.authKey);
        jsonObject.put("authkey", this.type.getNameOfType());
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

    public void setData(SocketMessageData socketMessageData) {
        // this.data =
    }

    public JSONObject getData() {
        return this.data;
    }

}
