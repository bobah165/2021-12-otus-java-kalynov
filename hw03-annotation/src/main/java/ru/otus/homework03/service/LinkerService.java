package ru.otus.homework03.service;

import ru.otus.homework03.model.Linker;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class LinkerService {

    public <T> List<Linker> getLinkerList(List<String> methodNames, Class<T> clazz) {
        return methodNames.stream()
                          .map(methodName -> converterToLinker(clazz, methodName))
                          .collect(Collectors.toList());
    }

    public <T> List<Linker> getMethodsByAnnotation(List<Linker> linkers, Class<T> clazz) {
        return linkers.stream()
                      .filter(linker -> isAnnotationExistInMethod(clazz, linker))
                      .collect(Collectors.toList());
    }

    private static <T> boolean isAnnotationExistInMethod(Class<T> clazz, Linker linker) {
        return linker.getAnnotations()
                     .stream()
                     .anyMatch(annotation -> annotation.annotationType()
                                                       .getName()
                                                       .equals(clazz.getName()));
    }

    private <T> Linker converterToLinker(Class<T> clazz, String methodName) {
        var method = getMethodByName(clazz, methodName);
        var annotations = method.getDeclaredAnnotations();
        var annotationList = Arrays.stream(annotations).collect(Collectors.toList());

        return new Linker().setMethod(method)
                           .setAnnotations(annotationList)
                           .setTestMethodGreen(true);
    }

    private static <T> Method getMethodByName(Class<T> clazz, String methodName) {
        Method method = null;
        try {
            method = clazz.getMethod(methodName);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return method;
    }
}
