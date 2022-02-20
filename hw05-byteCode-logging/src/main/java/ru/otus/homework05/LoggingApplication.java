package ru.otus.homework05;

import ru.otus.homework05.logging.TestLogging;
import ru.otus.homework05.service.ProxyService;

public class LoggingApplication {

    public static void main(String[] args) {
        var testLogging = ProxyService.createLogProxy(TestLogging.class);

        testLogging.calculation(6, 10);
        testLogging.calculation(13);
        testLogging.calculation();
        testLogging.anotherMethod();
        testLogging.calculation(2,3,"25");
    }
}
