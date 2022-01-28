package ru.otus.homework03.service;

import ru.otus.homework03.annotation.After;
import ru.otus.homework03.annotation.Before;
import ru.otus.homework03.annotation.Test;
import ru.otus.homework03.model.Linker;

import java.util.List;

public class InvokeService {

    private final CreateInstanceService createInstance;
    private final LinkerService linkerService;
    private final PrintResultService printResultService;

    public InvokeService(CreateInstanceService createInstance, LinkerService linkerService, PrintResultService printResultService) {
        this.createInstance = createInstance;
        this.linkerService = linkerService;
        this.printResultService = printResultService;
    }

    public <T> void invokeMethods(Class<T> clazz, List<String> methodNames) {
        var linkerList = linkerService.getLinkerList(methodNames, clazz);

        var testMethods = linkerService.getMethodsByAnnotation(linkerList, Test.class);
        var afterMethods = linkerService.getMethodsByAnnotation(linkerList, After.class);
        var beforeMethods = linkerService.getMethodsByAnnotation(linkerList, Before.class);

        testMethods.forEach(testMethod -> {
            T t =  createInstance.createInstance(clazz);
            invoke(beforeMethods, t);
            invoke(testMethod, t);
            invoke(afterMethods, t);
            System.out.println("--------------------------------------");
        });

        printResultService.print(testMethods);
    }

    private static <T> void invoke(Linker linker, T t) {
        try {
            linker.getMethod().invoke(t);
        } catch (Exception e) {
            System.out.println("!!!!!Test " + linker.getMethod().getName() + " is failed!!!!!");
            linker.setTestMethodGreen(false);
        }
    }

    private static <T> void invoke(List<Linker> linkers, T t) {
        linkers.stream()
               .findFirst()
               .ifPresent(linker -> {
                    try {
                        linker.getMethod().invoke(t);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
    }
}
