/**
 * The {@code RoomType} enum represents the type of a chat room.
 */
package com.sejacha.server;

public enum RoomType {
    PUBLIC(0),
    PRIVATE(1),
    BROADCAST(2);

    private final int intValue;

    /**
     * Constructs a {@code RoomType} with the specified integer value.
     *
     * @param intValue the integer value representing the room type
     */
    RoomType(int intValue) {
        this.intValue = intValue;
    }

    /**
     * Gets the integer value of the room type.
     *
     * @return the integer value of the room type
     */
    public int getNameOfType() {
        return intValue;
    }

    /**
     * Converts an integer value to the corresponding {@code RoomType}.
     *
     * @param value the integer value to convert
     * @return the {@code RoomType} corresponding to the integer value
     * @throws IllegalArgumentException if no enum constant corresponds to the
     *                                  integer value
     */
    public static RoomType fromInt(int value) {
        for (RoomType enumValue : RoomType.values()) {
            if (enumValue.intValue == value) {
                return enumValue;
            }
        }
        throw new IllegalArgumentException("No enum constant with int value " + value);
    }
}
