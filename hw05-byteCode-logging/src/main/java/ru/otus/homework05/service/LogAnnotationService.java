package ru.otus.homework05.service;

import ru.otus.homework05.annotation.Log;

import java.lang.reflect.Method;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class LogAnnotationService {

    public static <T> List<Method> classLogAnnotationScanner(Class<T> currentInterface) {
        List<Class> subClassesList = SubClassesService.getSubClasses(currentInterface);
        return subClassesList.stream()
                             .map(Class::getDeclaredMethods)
                             .flatMap(Stream::of)
                             .filter(method -> method.isAnnotationPresent(Log.class))
                             .collect(Collectors.toList());
    }

}
