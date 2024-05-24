package com.sejacha.server;

public enum UserState {
    UNVERIFIED(0),
    VERIFIED(1),
    BLOCKED(2);

    private final int intValue;

    UserState(int intValue) {
        this.intValue = intValue;
    }

    public int getNameOfType() {
        return intValue;
    }

    public static UserState fromInt(int value) {
        for (UserState enumValue : UserState.values()) {
            if (enumValue.intValue == value) {
                return enumValue;
            }
        }
        throw new IllegalArgumentException("No enum constant with int value " + value);
    }

}
