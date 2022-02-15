package ru.otus.homework05.service;

import ru.otus.homework05.exceptions.NoClassInApplicationException;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SubClassesService {

    public static <T> List<Class<?>> getSubClasses(Class<T> currentInterface) {
        var classLoader = currentInterface.getClassLoader();
        var packageName = currentInterface.getPackageName();
        var dirPath = packageName.replace(".", "/");
        var srcList = classLoader.getResource(dirPath);
        var files = new File(srcList.getFile()).listFiles();

        return find(currentInterface, packageName, files);
    }

    private static <T> List<Class<?>> find(Class<T> currentInterface, String packageName, File[] files) {
        var subClassList = new ArrayList<Class<?>>();
        for (var file : files) {
            if (file.isDirectory()) {
                var currentPackageName = packageName + "." + file.getName();
                var sList = find(currentInterface, currentPackageName, file.listFiles());
                subClassList.addAll(sList);
            } else {
                var subClassName = packageName + '.' + file.getName().split("\\.")[0];
                addSubClassToList(currentInterface, subClassName, subClassList);
            }
        }
        return subClassList;
    }

    @SuppressWarnings("unchecked")
    private static <T> void addSubClassToList(Class<T> currentInterface, String subClassName, List<Class<?>> subClassList) {
        try {
            var tempClass = Class.forName(subClassName);
            if (!tempClass.isInterface()) {
                var instance = (T) CreateInstanceService.createInstance(tempClass);
                if (isImplementationOfCurrentInterface(currentInterface, instance)) {
                    subClassList.add(Class.forName(subClassName));
                }
            }
        } catch (ClassNotFoundException e) {
            throw new NoClassInApplicationException("No subclass implementation or " + subClassName + " doesn't exist");
        }
    }

    private static <T> boolean isImplementationOfCurrentInterface(Class<T> currentInterface, T instance) {
        return Arrays.stream(instance.getClass().getInterfaces())
                     .anyMatch(method -> method.getName().equals(currentInterface.getName()));
    }
}
