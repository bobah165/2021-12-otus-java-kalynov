package ru.otus.homework05.service;

import ru.otus.homework05.handler.LoggingInvocationHandler;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.List;


public class ProxyService {

    public static <T> T createLogProxy(Class<T> interfaceClass) {
        var logMethods = LogAnnotationService.classLogAnnotationScanner(interfaceClass);
        var implClassName = logMethods.stream()
                                  .map(method -> method.getDeclaringClass().getName())
                                  .findFirst()
                                  .orElse(interfaceClass.getName());

        return wrapInvokedMethodInProxy(logMethods, implClassName, interfaceClass);
    }

    private static <T> T wrapInvokedMethodInProxy(List<Method> logMethods, String className, Class<T> clazz) {
        InvocationHandler handler = null;
        try {
            handler = new LoggingInvocationHandler<>(CreateInstanceService.createInstance(Class.forName(className)), logMethods);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return (T) Proxy.newProxyInstance(ProxyService.class.getClassLoader(),
                new Class<?>[]{clazz}, handler);
    }
}
