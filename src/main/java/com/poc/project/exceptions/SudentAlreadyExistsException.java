package com.poc.project.exceptions;

public class SudentAlreadyExistsException extends RuntimeException{
    public SudentAlreadyExistsException(String message) {
        super(message);
    }
}
