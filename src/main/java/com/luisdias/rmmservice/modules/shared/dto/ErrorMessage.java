package com.luisdias.rmmservice.modules.shared.dto;

public class ErrorMessage {

    private final String message;

    public ErrorMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public static ErrorMessage of(String message) {
        return new ErrorMessage(message);
    }
}
