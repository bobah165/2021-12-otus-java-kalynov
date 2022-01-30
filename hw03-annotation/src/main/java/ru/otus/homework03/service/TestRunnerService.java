package ru.otus.homework03.service;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.stream.Collectors;

public class TestRunnerService {

    private final InvokeService invokeService;

    public TestRunnerService(InvokeService invokeService) {
        this.invokeService = invokeService;
    }

    public <T> void runTests(Class<T> clazz) {
        var methods = clazz.getDeclaredMethods();
        var methodsNames = Arrays.stream(methods)
                                            .map(Method::getName)
                                            .collect(Collectors.toList());

        System.out.println("\n--------------------------------------\nStart Tests");
        System.out.println("--------------------------------------");
        invokeService.invokeMethods(clazz, methodsNames);
        System.out.println("--------------------------------------\n End Tests");
    }
}
