/**
 * The {@code UserState} enum represents the possible states of a user in the system.
 * Each state is associated with an integer value.
 */
package com.sejacha.server;

public enum UserState {
    UNVERIFIED(0),
    VERIFIED(1),
    BLOCKED(2);

    private final int intValue;

    /**
     * Constructs a {@code UserState} with the specified integer value.
     *
     * @param intValue the integer value associated with the user state
     */
    UserState(int intValue) {
        this.intValue = intValue;
    }

    /**
     * Returns the integer value associated with this {@code UserState}.
     *
     * @return the integer value of this user state
     */
    public int getNameOfType() {
        return intValue;
    }

    /**
     * Returns the {@code UserState} corresponding to the specified integer value.
     *
     * @param value the integer value of the user state
     * @return the {@code UserState} corresponding to the specified value
     * @throws IllegalArgumentException if no user state corresponds to the
     *                                  specified value
     */
    public static UserState fromInt(int value) {
        for (UserState enumValue : UserState.values()) {
            if (enumValue.intValue == value) {
                return enumValue;
            }
        }
        throw new IllegalArgumentException("No enum constant with int value " + value);
    }
}
