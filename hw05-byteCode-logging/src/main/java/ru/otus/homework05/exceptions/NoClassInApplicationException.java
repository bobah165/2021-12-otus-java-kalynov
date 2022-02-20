package ru.otus.homework05.exceptions;

public class NoClassInApplicationException extends RuntimeException {
    private final String message;

    public NoClassInApplicationException(String message) {
        this.message = message;
    }
}
