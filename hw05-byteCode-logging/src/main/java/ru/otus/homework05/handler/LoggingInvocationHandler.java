package ru.otus.homework05.handler;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class LoggingInvocationHandler<T> implements InvocationHandler {
    private final T myClass;

    public LoggingInvocationHandler(T myClass) {this.myClass = myClass;}

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("invoking method:" + method);
        return method.invoke(myClass, args);
    }
}
