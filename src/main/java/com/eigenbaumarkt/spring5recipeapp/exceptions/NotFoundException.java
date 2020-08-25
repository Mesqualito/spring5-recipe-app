package com.eigenbaumarkt.spring5recipeapp.exceptions;

public class NotFoundException extends RuntimeException {

    public NotFoundException() {
        super();
    }

    public NotFoundException(String message) {
        super("With message only: " + message);
    }

    public NotFoundException(String message, Throwable cause) {
        super("With message and cause: " + message, cause);
    }

}
