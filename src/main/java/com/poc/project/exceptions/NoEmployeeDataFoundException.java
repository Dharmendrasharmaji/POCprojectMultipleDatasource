package com.poc.project.exceptions;

public class NoEmployeeDataFoundException extends RuntimeException{
    public NoEmployeeDataFoundException(String message) {
        super(message);
    }
}
