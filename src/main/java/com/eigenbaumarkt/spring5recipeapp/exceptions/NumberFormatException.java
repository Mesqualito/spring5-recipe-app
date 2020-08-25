package com.eigenbaumarkt.spring5recipeapp.exceptions;

public class NumberFormatException extends RuntimeException {

    public NumberFormatException() {
        super();
    }

    public NumberFormatException(String message) {
        super("With message only: " + message);
    }

    public NumberFormatException(String message, Throwable cause) {
        super("With message and cause: " + message, cause);
    }

}
