package com.rbc.code.challenge.error;

public class InvalidDateException extends Exception {
    public InvalidDateException() {
        super();
    }

    public InvalidDateException(String messasge) {
        super(messasge);
    }
}
