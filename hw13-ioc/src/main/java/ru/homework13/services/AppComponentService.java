package ru.homework13.services;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class AppComponentService {

    public <C> C getComponent(Class<C> componentClass, List<Object> appComponents) {
        if (componentClass.isInterface()) {
            return getAppComponentByPredicate(appComponents, component -> isInterfacesEqual(componentClass, component));
        }
        return getAppComponentByPredicate(appComponents, component -> isClassNamesEquals(componentClass, component));
    }

    private <C> C getAppComponentByPredicate( List<Object> appComponents, Predicate<Object> predicate) {
        return (C) appComponents.stream()
                                .filter(predicate)
                                .findFirst()
                                .get();
    }

    private <C> boolean isClassNamesEquals(Class<C> componentClass, Object component) {
        return component.getClass().getName().equals(componentClass.getName());
    }

    private <C> boolean isInterfacesEqual(Class<C> componentClass, Object component) {
        return Arrays.stream(component.getClass().getInterfaces())
                     .map(Class::getName)
                     .collect(Collectors.toList())
                     .contains(componentClass.getName());
    }
}
