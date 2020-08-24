package com.eigenbaumarkt.spring5recipeapp.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


// If just setting the HTTP status - use @ReponseStatus
// @ResponseStatus(HttpStatus.NOT_FOUND)
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
