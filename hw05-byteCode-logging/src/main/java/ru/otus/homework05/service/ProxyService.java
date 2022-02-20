package ru.otus.homework05.service;

import ru.otus.homework05.exceptions.NoClassInApplicationException;
import ru.otus.homework05.handler.LoggingInvocationHandler;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;


public class ProxyService {

    public static <T> T createLogProxy(Class<T> interfaceClass) {
        var implClassName = LogAnnotationService.getClassNameWithLogAnnotation(interfaceClass);
        return wrapInvokedMethodInProxy(implClassName, interfaceClass);
    }

    @SuppressWarnings("unchecked")
    private static <T> T wrapInvokedMethodInProxy(String className, Class<T> clazz) {
        InvocationHandler handler;
        try {
            handler = new LoggingInvocationHandler<>(CreateInstanceService.createInstance(Class.forName(className)));
        } catch (ClassNotFoundException e) {
            throw new NoClassInApplicationException("No class implementation or " + className + " doesn't exist");
        }
        return (T) Proxy.newProxyInstance(ProxyService.class.getClassLoader(),
                new Class<?>[]{clazz}, handler);
    }
}
