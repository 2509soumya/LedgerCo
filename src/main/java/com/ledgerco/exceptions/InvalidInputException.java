package com.ledgerco.exceptions;

public class InvalidInputException extends RuntimeException{
    public InvalidInputException(String command) {
        super(String .format("Invalid command or input not valid for command %s",command));
    }
}
