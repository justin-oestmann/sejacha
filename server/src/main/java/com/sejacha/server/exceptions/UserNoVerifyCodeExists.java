package com.sejacha.server.exceptions;

/**
 * This exception is thrown when no verification code exists for a user.
 */
public class UserNoVerifyCodeExists extends Exception {

    /**
     * Constructs a new UserNoVerifyCodeExists with the specified detail message.
     *
     * @param message the detail message (which is saved for later retrieval by the
     *                getMessage() method)
     */
    public UserNoVerifyCodeExists(String message) {
        // Call the constructor of the superclass and pass the message
        super(message);
    }
}
