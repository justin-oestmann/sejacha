package com.sejacha.server.exceptions;

public class SocketMessageIsNotNewException extends Exception {
    public SocketMessageIsNotNewException(String message) {
        // Rufe den Konstruktor der Superklasse auf und übergebe die Nachricht
        super(message);
    }
}
