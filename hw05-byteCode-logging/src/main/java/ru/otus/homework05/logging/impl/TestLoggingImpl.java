package ru.otus.homework05.logging.impl;

import ru.otus.homework05.annotation.Log;
import ru.otus.homework05.logging.TestLogging;

public class TestLoggingImpl implements TestLogging {

    @Log
    @Override
    public void calculation(int param) {}
}
