package ru.otus.homework05.handler;

import ru.otus.homework05.service.ArgsService;
import ru.otus.homework05.service.LogAnnotationService;
import ru.otus.homework05.service.MethodParametersService;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class LoggingInvocationHandler<T> implements InvocationHandler {
    private final T myClass;

    public LoggingInvocationHandler(T myClass) {
        this.myClass = myClass;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (isInvokedMethodHasLogAnnotation(method)) {
            System.out.println("execute method: " + method.getName() + ", param: " + ArgsService.getArgs(args));
        }
        return method.invoke(myClass, args);
    }


    private boolean isInvokedMethodHasLogAnnotation(Method method) {
        var logMethods = LogAnnotationService.getMethodsWithLogAnnotation(method.getDeclaringClass());
        return logMethods.stream()
                         .anyMatch(logMethod -> logMethod.getName().equals(method.getName())
                                 && MethodParametersService.isEqualParametersInMethods(logMethod, method));
    }
}
