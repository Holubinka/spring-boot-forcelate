package com.holubinka.controller.exception;

public class NotMatchedPasswordsException extends RuntimeException {
    private String message;

    public NotMatchedPasswordsException(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

}
