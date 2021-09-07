package com.poc.project.exceptions;

public class NoStudentDataException extends RuntimeException{
    public NoStudentDataException(String message) {
        super(message);
    }
}
