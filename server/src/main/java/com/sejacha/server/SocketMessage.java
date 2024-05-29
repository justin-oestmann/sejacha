package com.sejacha.server;

import org.json.JSONObject;

import com.sejacha.server.exceptions.SocketMessageIsNotNewException;

public class SocketMessage {

    private boolean isLoadedMessage = false;
    private String authKey = null;
    private SocketMessageType type;
    private JSONObject data = null;

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

    public String getAuthKey() {
        return this.authKey;
    }

    public SocketMessageType getType() {
        return this.type;
    }

    public void setType(SocketMessageType socketMessageType) throws SocketMessageIsNotNewException {
        if (!this.isLoadedMessage) {
            this.type = socketMessageType;
            return;
        }
        throw new SocketMessageIsNotNewException("This Message is a loaded message and can't be changed");
    }

    public void setAuthKey(String authKey) throws SocketMessageIsNotNewException {
        if (!this.isLoadedMessage) {
            this.authKey = authKey;
            return;
        }
        throw new SocketMessageIsNotNewException("This Message is a loaded message and can't be changed");
    }

    public void setData(JSONObject socketMessageData) throws SocketMessageIsNotNewException {
        if (!this.isLoadedMessage) {
            this.data = socketMessageData;
            return;
        }
        throw new SocketMessageIsNotNewException("This Message is a loaded message and can't be changed");
    }

    public JSONObject getData() {
        return this.data;
    }

}
