package ru.homework13.services;

import ru.homework13.exception.NoInstanceCreatedException;

public class InstanceService {

    public Object create(Class<?> clazz) {
        try {
            var constructor = clazz.getConstructor();
            return constructor.newInstance();
        } catch (Exception e) {
            throw new NoInstanceCreatedException("No instance is created");
        }
    }
}
