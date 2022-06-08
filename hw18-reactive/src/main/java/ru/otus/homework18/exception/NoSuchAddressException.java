package ru.otus.homework18.exception;

public class NoSuchAddressException extends RuntimeException{
    private String message;

    public NoSuchAddressException(String message) {
        super(message);
    }
}
