package com.sejacha.server.exceptions;

public class UserInvalidStateException extends Exception {
    public UserInvalidStateException(String message) {
        // Rufe den Konstruktor der Superklasse auf und übergebe die Nachricht
        super(message);
    }
}
