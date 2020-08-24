package com.eigenbaumarkt.spring5recipeapp.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

// custom HTML Client exception; will be invoked on HTML 404
// Browser will now show HTML Client error 404 instead of Server Error 500
@ResponseStatus(HttpStatus.NOT_FOUND)
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
