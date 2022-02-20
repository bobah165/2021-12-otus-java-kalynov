package ru.otus.homework05.logging.impl;

import ru.otus.homework05.annotation.Log;
import ru.otus.homework05.logging.TestLogging;

public class TestLoggingImpl implements TestLogging {


    @Override
    public void calculation() {}

    @Log
    @Override
    public void calculation(int param) {}

    @Override
    public void calculation(int param1, int param2) {}

    @Log
    @Override
    public void calculation(int param1, int param2, String param3) {}

    @Log
    @Override
    public void anotherMethod() {}
}
