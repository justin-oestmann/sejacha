package com.sejacha.server.exceptions;

public class UserNoVerifyCodeExists extends Exception {
    public UserNoVerifyCodeExists(String message) {
        // Rufe den Konstruktor der Superklasse auf und übergebe die Nachricht
        super(message);
    }
}
