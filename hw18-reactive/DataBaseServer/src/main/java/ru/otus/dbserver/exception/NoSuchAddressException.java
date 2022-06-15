package ru.otus.dbserver.exception;

public class NoSuchAddressException extends RuntimeException{
    private String message;

    public NoSuchAddressException(String message) {
        super(message);
    }
}
