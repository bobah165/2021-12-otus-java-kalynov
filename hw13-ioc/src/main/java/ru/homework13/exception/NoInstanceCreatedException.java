package ru.homework13.exception;

public class NoInstanceCreatedException extends RuntimeException {
    public NoInstanceCreatedException(String message) {
        super(message);
    }
}
