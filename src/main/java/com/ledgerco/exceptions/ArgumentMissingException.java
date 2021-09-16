package com.ledgerco.exceptions;

public class ArgumentMissingException extends RuntimeException{
    public ArgumentMissingException(Exception ex) {
        super("FileName Argument is missing",ex);
    }
}
