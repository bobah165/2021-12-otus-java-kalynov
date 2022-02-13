package ru.otus.homework05.handler;

import ru.otus.homework05.service.ArgsService;
import ru.otus.homework05.service.MethodParametersService;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.List;

public class LoggingInvocationHandler<T> implements InvocationHandler {
    private final T myClass;
    private final List<Method> logMethods;

    public LoggingInvocationHandler(T myClass, List<Method> logMethods) {
        this.myClass = myClass;
        this.logMethods = logMethods;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (isInvokedMethodHasLogAnnotation(method)) {
            System.out.println("execute method: " + method.getName() + ", param: " + ArgsService.getArgs(args));
        }
        return method.invoke(myClass, args);
    }

    private boolean isInvokedMethodHasLogAnnotation(Method method) {
        return logMethods.stream()
                         .anyMatch(logMethod -> logMethod.getName().equals(method.getName())
                                 && MethodParametersService.isEqualParametersInMethods(logMethod, method));
    }
}
