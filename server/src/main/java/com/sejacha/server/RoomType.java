package com.sejacha.server;

public enum RoomType {
    PUBLIC(0),
    PRIVATE(1),
    BROADCAST(2);

    private final int intValue;

    RoomType(int intValue) {
        this.intValue = intValue;
    }

    public int getNameOfType() {
        return intValue;
    }

    public static RoomType fromInt(int value) {
        for (RoomType enumValue : RoomType.values()) {
            if (enumValue.intValue == value) {
                return enumValue;
            }
        }
        throw new IllegalArgumentException("No enum constant with int value " + value);
    }

}
