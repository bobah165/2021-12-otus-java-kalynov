package ru.otus.homework05.service;

import ru.otus.homework05.annotation.Log;
import ru.otus.homework05.exceptions.NoClassInApplicationException;

import java.lang.reflect.Method;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class LogAnnotationService {

    public static <T> List<Method> getMethodsWithLogAnnotation(Class<T> currentInterface) {
        var subClassesList = SubClassesService.getSubClasses(currentInterface);
        return subClassesList.stream()
                             .map(Class::getDeclaredMethods)
                             .flatMap(Stream::of)
                             .filter(method -> method.isAnnotationPresent(Log.class))
                             .collect(Collectors.toList());
    }

    public static <T> String getClassNameWithLogAnnotation(Class<T> currentInterface) {
        var logMethods = getMethodsWithLogAnnotation(currentInterface);
        return logMethods.stream()
                         .map(method -> method.getDeclaringClass().getName())
                         .findFirst()
                         .orElseGet(()-> processNoLogAnnotationCase(currentInterface));
    }

    private static <T> String processNoLogAnnotationCase(Class<T> currentInterface) {
        var subClassesList = SubClassesService.getSubClasses(currentInterface);
        return subClassesList.stream()
                             .findFirst()
                             .orElseThrow(()-> new NoClassInApplicationException("Interface " + currentInterface.getName() + " has no any implementations"))
                             .getName();
    }

}
