package ru.otus.homework05.service;

import ru.otus.homework05.annotation.Log;

import java.io.File;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class LogAnnotationService {

    public static <T> List<Method> classLogAnnotationScanner(Class<T> clazz) {
        List<Class> subClassesList = findSubClasses(clazz);
        return subClassesList.stream()
                             .map(Class::getDeclaredMethods)
                             .flatMap(Stream::of)
                             .filter(method -> method.isAnnotationPresent(Log.class))
                             .collect(Collectors.toList());
    }

    private static <T> List<Class> findSubClasses(Class<T> clazz) {
        ClassLoader classLoader = clazz.getClassLoader();
        var packageName = clazz.getPackageName();
        var dirPath = packageName.replace(".", "/");
        var srcList = classLoader.getResource(dirPath);
        System.out.println();

        File dirFile = new File(srcList.getFile());
        File[] files = dirFile.listFiles();
        return findSubClasses(clazz, packageName, files);
    }

    private static <T> List<Class> findSubClasses(Class<T> clazz, String packageName, File[] files) {
        List<Class> subClassList = new ArrayList<>();
        for (File file : files) {
            if (file.isDirectory()) {
                List<Class> sList = findSubClasses(clazz, packageName + "." + file.getName(), file.listFiles());
                subClassList.addAll(sList);
            } else {
                String subClassName = packageName + '.' + file.getName().split("\\.")[0];
                try {
                    var tempClass = Class.forName(subClassName);
                    if (!tempClass.isInterface()) {
                        T instance = (T) CreateInstanceService.createInstance(tempClass);
                        if (Arrays.stream(instance.getClass().getInterfaces())
                                  .anyMatch(method -> method.getName().equals(clazz.getName()))) {
                            subClassList.add(Class.forName(subClassName));
                        }
                    }
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
        return subClassList;
    }
}
