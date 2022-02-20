package ru.otus.homework05.exceptions;

public class InstanceIsNotCreatedException extends RuntimeException {
    private final String message;

    public InstanceIsNotCreatedException(String message) {
        this.message = message;
    }
}
