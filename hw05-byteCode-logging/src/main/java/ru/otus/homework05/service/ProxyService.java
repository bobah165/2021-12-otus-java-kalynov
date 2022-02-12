package ru.otus.homework05.service;

import ru.otus.homework05.handler.LoggingInvocationHandler;
import ru.otus.homework05.logging.TestLogging;
import ru.otus.homework05.logging.impl.TestLoggingImpl;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;


public class ProxyService {

    public static <T> TestLogging createLogProxy(Class<T> clazz) {
        var logMethods = LogAnnotationService.classLogAnnotationScanner(clazz);
        if (!logMethods.isEmpty()) {
            var className = logMethods.stream()
                                      .map(method -> method.getDeclaringClass().getName())
                                      .findFirst().get();
            InvocationHandler handler = null;
            try {
                handler = new LoggingInvocationHandler<>(CreateInstanceService.createInstance(Class.forName(className)));
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            return (TestLogging) Proxy.newProxyInstance(ProxyService.class.getClassLoader(),
                    new Class<?>[]{TestLogging.class}, handler);
        }
        return new TestLoggingImpl();
    }
}
