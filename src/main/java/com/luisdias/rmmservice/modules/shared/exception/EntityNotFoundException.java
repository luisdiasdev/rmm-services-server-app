package com.luisdias.rmmservice.modules.shared.exception;

public class EntityNotFoundException extends RuntimeException {

    public EntityNotFoundException(String entityName) {
        super("The resource " + entityName + " was not found.");
    }
}