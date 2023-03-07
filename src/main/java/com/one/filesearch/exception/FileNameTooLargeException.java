package com.one.filesearch.exception;

public class FileNameTooLargeException extends RuntimeException{
    public FileNameTooLargeException(String message) {
        super(message);
    }
}
