package ru.homework13.appcontainer;

import ru.homework13.appcontainer.api.AppComponent;
import ru.homework13.appcontainer.api.AppComponentsContainer;
import ru.homework13.appcontainer.api.AppComponentsContainerConfig;
import ru.homework13.services.AppComponentService;
import ru.homework13.services.InstanceService;
import ru.homework13.services.MethodService;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AppComponentsContainerImpl implements AppComponentsContainer {

    private final List<Object> appComponents = new ArrayList<>();
    private final Map<String, Object> appComponentsByName = new HashMap<>();

    private final MethodService methodService;
    private final AppComponentService appComponentService;
    private final InstanceService instanceService;

    public AppComponentsContainerImpl(Class<?> initialConfigClass) {
        methodService = new MethodService();
        appComponentService = new AppComponentService();
        instanceService = new InstanceService();
        processConfig(initialConfigClass);
    }

    private void processConfig(Class<?> configClass) {
        checkConfigClass(configClass);
        var methods = methodsListWithAnnotation(configClass, AppComponent.class);
        var instanceOfConfigClass = instanceService.create(configClass);
        var classAndBeanNames = new HashMap<String, String>();

        methods.forEach(method -> {
            var beanName = method.getAnnotation(AppComponent.class).name();
            var result = invokeMethod(method, instanceOfConfigClass, classAndBeanNames);
            classAndBeanNames.put(method.getName().toLowerCase(), beanName);
            appComponents.add(result);
            appComponentsByName.put(beanName, result);
        });
    }

    @Override
    public <C> C getAppComponent(Class<C> componentClass) {
        return appComponentService.getComponent(componentClass, appComponents);
    }

    @Override
    public <C> C getAppComponent(String componentName) {
        return (C) appComponentsByName.get(componentName);
    }

    private Object invokeMethod(Method method, Object instanceOfConfigClass, Map<String, String> classBeanName) {
        return methodService.invokeMethod(method,instanceOfConfigClass,classBeanName, appComponentsByName);
    }

    private void checkConfigClass(Class<?> configClass) {
        if (!configClass.isAnnotationPresent(AppComponentsContainerConfig.class)) {
            throw new IllegalArgumentException(String.format("Given class is not config %s", configClass.getName()));
        }
    }

    private <T extends Annotation> List<Method> methodsListWithAnnotation(Class<?> configClass, Class<T> annotationClass) {
        return methodService.sortedMethodsListWithAnnotation(configClass,annotationClass);
    }
}
