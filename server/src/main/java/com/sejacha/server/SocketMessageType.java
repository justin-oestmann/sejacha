package com.sejacha.server;

public enum SocketMessageType {
    LOGIN("login"),
    REGISTER("register"),
    LOGOUT("logout"),
    NEWMESSAGE("newmessage"),
    ROOM_JOIN("room_join"),
    ROOM_JOIN_W_PASSWORD("room_join_w_password"),
    ROOM_LEAVE("room_leave"),
    ROOM_CREATE("room_create"),
    ROOM_GETINFO("room_getinfo"),
    ROOM_INVITE_CONTACT("room_getinfo"),
    CONTACT_ADD("contact_add"),
    CONTACT_REMOVE("contact_remove"),
    CONTACT_CREATE_DM_ROOM("contact_remove");

    private final String nameOfType;

    SocketMessageType(String nameOfType) {
        this.nameOfType = nameOfType;
    }

    public String getNameOfType() {
        return nameOfType;
    }

    public static SocketMessageType fromString(String text) {
        for (SocketMessageType type : SocketMessageType.values()) {
            if (type.nameOfType.equalsIgnoreCase(text)) {
                return type;
            }
        }
        throw new IllegalArgumentException("No enum constant with text " + text);
    }

}
