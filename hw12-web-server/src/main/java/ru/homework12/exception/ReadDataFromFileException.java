package ru.homework12.exception;

import java.io.IOException;

public class ReadDataFromFileException extends IOException {
    public ReadDataFromFileException(String message) {
        super(message);
    }
}
