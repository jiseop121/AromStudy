package arom.springtoy.domain.exception;

public class TodolistException extends RuntimeException{

    public TodolistException() {
    }

    public TodolistException(String message) {
        super(message);
    }
}
