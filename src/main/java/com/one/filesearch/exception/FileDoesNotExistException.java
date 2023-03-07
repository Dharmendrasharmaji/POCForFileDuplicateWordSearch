package com.one.filesearch.exception;

public class FileDoesNotExistException extends RuntimeException{

    public FileDoesNotExistException(String message) {
        super(message);
    }
}
