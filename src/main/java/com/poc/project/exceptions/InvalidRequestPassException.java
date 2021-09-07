package com.poc.project.exceptions;

public class InvalidRequestPassException extends RuntimeException{
    public InvalidRequestPassException(String message) {
        super(message);
    }
}
