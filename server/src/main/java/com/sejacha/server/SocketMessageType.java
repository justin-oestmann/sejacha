/**
 * The {@code SocketMessageType} enum defines a set of constants representing various types of socket messages.
 * Each enum constant has an associated string name which can be used to identify the message type.
 */
package com.sejacha.server;

public enum SocketMessageType {
    PING("ping"),
    NOTIFY("notify"),

    VERIFY("verify"),
    VERIFY_RESPONSE_SUCCESS("verify_response_success"),
    VERIFY_RESPONSE_FAIL("verify_response_fail"),

    LOGIN("login"),
    LOGIN_RESPONSE_SUCCESS("login_response_success"),
    LOGIN_RESPONSE_FAIL("login_response_fail"),

    REGISTER("register"),
    REGISTER_RESPONSE_SUCCESS("register_response_success"),
    REGISTER_RESPONSE_FAIL("register_response_fail"),

    LOGOUT("logout"),
    LOGOUT_RESPONSE_SUCCESS("logout_response_success"),
    LOGOUT_RESPONSE_FAIL("logout_response_fail"),

    NEWMESSAGE("newmessage"),
    NEWMESSAGE_RESPONSE_SUCCESS("newmessage_response_success"),
    NEWMESSAGE_RESPONSE_FAIL("newmessage_response_fail"),

    ROOM_JOIN("room_join"),
    ROOM_JOIN_RESPONSE_SUCCESS("room_join_response_success"),
    ROOM_JOIN_RESPONSE_FAIL("room_join_response_fail"),

    ROOM_JOIN_W_PASSWORD("room_join_w_password"),
    ROOM_JOIN_W_PASSWORD_RESPONSE_SUCCESS("room_join_w_password_response_success"),
    ROOM_JOIN_W_PASSWORD_RESPONSE_FAIL("room_join_w_password_response_fail"),

    ROOM_LEAVE("room_leave"),
    ROOM_LEAVE_RESPONSE_SUCCESS("room_leave_response_success"),
    ROOM_LEAVE_RESPONSE_FAIL("room_leave_response_fail"),

    ROOM_CREATE("room_create"),
    ROOM_CREATE_RESPONSE_SUCCESS("room_create_response_success"),
    ROOM_CREATE_RESPONSE_FAIL("room_create_response_fail"),

    ROOM_GETINFO("room_getinfo"),
    ROOM_GETINFO_RESPONSE_SUCCESS("room_getinfo_response_success"),
    ROOM_GETINFO_RESPONSE_FAIL("room_getinfo_response_fail"),

    ROOM_INVITE_CONTACT("room_invite_contact"),
    ROOM_INVITE_CONTACT_RESPONSE_SUCCESS("room_invite_contact_response_success"),
    ROOM_INVITE_CONTACT_RESPONSE_FAIL("room_invite_contact_response_fail"),

    CONTACT_ADD("contact_add"),
    CONTACT_ADD_RESPONSE_SUCCESS("contact_add_response_success"),
    CONTACT_ADD_RESPONSE_FAIL("contact_add_response_fail"),

    CONTACT_REMOVE("contact_remove"),
    CONTACT_REMOVE_RESPONSE_SUCCESS("contact_remove_response_success"),
    CONTACT_REMOVE_RESPONSE_FAIL("contact_remove_response_fail"),

    CONTACT_CREATE_DM_ROOM("contact_create_dm_room"),
    CONTACT_CREATE_DM_ROOM_RESPONSE_SUCCESS("contact_create_dm_room_response_success"),
    CONTACT_CREATE_DM_ROOM_RESPONSE_FAIL("contact_create_dm_room_response_fail");

    private final String nameOfType;

    /**
     * Constructs a {@code SocketMessageType} with the specified string name.
     *
     * @param nameOfType the string name associated with the message type
     */
    SocketMessageType(String nameOfType) {
        this.nameOfType = nameOfType;
    }

    /**
     * Returns the string name associated with the message type.
     *
     * @return the string name of the message type
     */
    public String getNameOfType() {
        return nameOfType;
    }

    /**
     * Returns the {@code SocketMessageType} constant corresponding to the specified
     * string name.
     *
     * @param text the string name of the message type
     * @return the corresponding {@code SocketMessageType} constant
     * @throws IllegalArgumentException if no constant with the specified string
     *                                  name exists
     */
    public static SocketMessageType fromString(String text) {
        for (SocketMessageType type : SocketMessageType.values()) {
            if (type.nameOfType.equalsIgnoreCase(text)) {
                return type;
            }
        }
        throw new IllegalArgumentException("No enum constant with text " + text);
    }
}
