package ru.otus.homework05.service;


import ru.otus.homework05.exceptions.InstanceIsNotCreatedException;

public class CreateInstanceService {

    public static  <T> T createInstance(Class<T> clazz) {
        T t;
        try {
            var constructor = clazz.getConstructor();
            t = constructor.newInstance();
        } catch (Exception e) {
            throw new InstanceIsNotCreatedException("Can't create instance of " + clazz.getName() + " class");
        }
        return t;
    }
}
