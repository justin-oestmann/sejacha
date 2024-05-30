package com.sejacha.server.exceptions;

/**
 * This exception is thrown when a socket message is not in a new state.
 */
public class SocketMessageIsNotNewException extends Exception {

    /**
     * Constructs a new SocketMessageIsNotNewException with the specified detail
     * message.
     *
     * @param message the detail message (which is saved for later retrieval by the
     *                getMessage() method)
     */
    public SocketMessageIsNotNewException(String message) {
        // Call the constructor of the superclass and pass the message
        super(message);
    }
}
