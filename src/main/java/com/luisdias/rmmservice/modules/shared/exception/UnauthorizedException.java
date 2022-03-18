package com.luisdias.rmmservice.modules.shared.exception;

public class UnauthorizedException extends RuntimeException {

    private static final String MESSAGE = "Not Authorized";

    public UnauthorizedException() {
        super(MESSAGE);
    }
}