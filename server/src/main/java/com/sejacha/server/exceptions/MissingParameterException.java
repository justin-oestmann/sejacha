package com.sejacha.server.exceptions;

/**
 * This exception is thrown when a required parameter is missing or incorrectly
 * filled.
 */
public class MissingParameterException extends Exception {

    /**
     * Constructs a new MissingParameterException with the specified parameter name.
     *
     * @param parameterName the name of the missing or incorrectly filled parameter
     */
    public MissingParameterException(String parameterName) {
        // Call the constructor of the superclass and pass the formatted message
        super("The parameter " + parameterName + " is missing or not correctly filled");
    }
}
