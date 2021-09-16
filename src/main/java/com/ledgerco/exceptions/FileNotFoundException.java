package com.ledgerco.exceptions;

public class FileNotFoundException extends RuntimeException{
    public FileNotFoundException(String filename,Exception ex) {
        super(String .format("File %s could not be found",filename),ex);
    }
}
