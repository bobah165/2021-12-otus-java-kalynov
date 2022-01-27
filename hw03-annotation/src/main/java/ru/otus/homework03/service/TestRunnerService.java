package ru.otus.homework03.service;

import ru.otus.homework03.AnnotationTest;
import ru.otus.homework03.annotation.After;
import ru.otus.homework03.annotation.Before;
import ru.otus.homework03.annotation.Test;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class TestRunnerService {

    public static <T> void runTests(Class<T> clazz) throws Exception {
        Method[] methods = clazz.getDeclaredMethods();
        var methodsNames = Arrays.stream(methods)
                                 .map(Method::getName)
                                 .collect(Collectors.toList());

        var mapAnnotationMethods = getMap(methodsNames, clazz);
        invokeTestMethods(mapAnnotationMethods);
        System.out.println("End tests");
    }

    private static void invokeTestMethods(Map<Annotation, List<Method>> annotationListMap) {
        annotationListMap.forEach((key, value) -> {
            if (key instanceof Test) {
                value.forEach(method -> {
                    AnnotationTest annotationTest = new AnnotationTest();
                    try {
                        invokeBeforeMethods(annotationListMap,annotationTest);
                        method.invoke(annotationTest);
                        invokeAfterMethods(annotationListMap,annotationTest);
                        System.out.println("------------------------------");
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        e.printStackTrace();
                    }
                });
            }
        });
    }


    private static void invokeAfterMethods(Map<Annotation, List<Method>> annotationListMap, AnnotationTest annotationTest) {
        annotationListMap.forEach((key, value) -> {
            if (key instanceof After) {
                value.forEach(method -> {
                    try {
                        method.invoke(annotationTest);
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        e.printStackTrace();
                    }
                });
            }
        });
    }

    private static void invokeBeforeMethods(Map<Annotation, List<Method>> annotationListMap, AnnotationTest annotationTest) {
        annotationListMap.forEach((key, value) -> {
            if (key instanceof Before) {
                value.forEach(method -> {
                    try {
                        method.invoke(annotationTest);
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        e.printStackTrace();
                    }
                });
            }
        });
    }


    private static <T> Map<Annotation, List<Method>> getMap(List<String> methodNames, Class<T> clazz) throws Exception {
        Map<Annotation, List<Method>> annotationMethodMethod = new HashMap<>();
        for (String methodName: methodNames) {
            Method method = clazz.getMethod(methodName);
            Annotation[] annotations = method.getDeclaredAnnotations();
            for (Annotation annotation : annotations) {
                annotation.annotationType();
                if (annotationMethodMethod.containsKey(annotation)) {
                    annotationMethodMethod.computeIfPresent(annotation, (k, v) -> {
                        v.add(method);
                        return v;
                    });
                } else {
                    List<Method> methods = new ArrayList<>();
                    methods.add(method);
                    annotationMethodMethod.put(annotation, methods);
                }
            }
        }
        return annotationMethodMethod;
    }
}
