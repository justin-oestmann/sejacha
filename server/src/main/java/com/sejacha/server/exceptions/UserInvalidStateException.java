package com.sejacha.server.exceptions;

/**
 * This exception is thrown when a user is in an invalid state.
 */
public class UserInvalidStateException extends Exception {

    /**
     * Constructs a new UserInvalidStateException with the specified detail message.
     *
     * @param message the detail message (which is saved for later retrieval by the
     *                getMessage() method)
     */
    public UserInvalidStateException(String message) {
        // Call the constructor of the superclass and pass the message
        super(message);
    }
}
