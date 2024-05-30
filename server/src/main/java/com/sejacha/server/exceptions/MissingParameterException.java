package com.sejacha.server.exceptions;

public class MissingParameterException extends Exception {
    public MissingParameterException(String parameterName) {
        super("Der Parameter " + parameterName + " ist leer oder nicht Korrekt ausgefüllt");
    }
}