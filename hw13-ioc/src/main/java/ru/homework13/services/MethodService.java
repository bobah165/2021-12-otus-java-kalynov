package ru.homework13.services;

import ru.homework13.appcontainer.api.AppComponent;
import ru.homework13.exception.NoSuchMethodException;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MethodService {

    public Object invokeMethod(Method method, Object instanceOfConfigClass,
                               Map<String, String> classBeanName, Map<String, Object> appComponentsByName) {
        try {
            var methodParameters = method.getParameterTypes();
            if (methodParameters.length != 0) {
                var parameters = getParameters(methodParameters, classBeanName, appComponentsByName);
                return method.invoke(instanceOfConfigClass, parameters);
            }
            return method.invoke(instanceOfConfigClass);
        } catch (Exception e) {
            throw new NoSuchMethodException("No method result is created");
        }
    }

    public <T extends Annotation> List<Method> sortedMethodsListWithAnnotation(Class<?> configClass,
                                                                               Class<T> annotationClass) {
        return Arrays.stream(configClass.getDeclaredMethods())
                     .filter(method -> method.isAnnotationPresent(annotationClass))
                     .collect(Collectors.groupingBy(method -> method.getAnnotation(AppComponent.class).order()))
                     .entrySet()
                     .stream()
                     .sorted(Map.Entry.comparingByKey())
                     .map(Map.Entry::getValue)
                     .flatMap(List::stream)
                     .collect(Collectors.toList());
    }

    private Object[] getParameters(Class<?>[] methodParameters, Map<String, String> classBeanName,
                                   Map<String, Object> appComponentsByName) {
        List<Object> parametersValue = new ArrayList<>();
        Arrays.stream(methodParameters).forEach(param -> {
            var className = getClassName(param);
            var beanName = classBeanName.getOrDefault(className.toLowerCase(), param.getName());
            parametersValue.add(appComponentsByName.get(beanName));
        });
        return parametersValue.toArray();
    }

    private String getClassName(Class<?> param) {
        var name = param.getName().split("\\.");
        return name[name.length - 1];
    }
}
