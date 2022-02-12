package ru.otus.homework05.service;


public class CreateInstanceService {

    public static  <T> T createInstance(Class<T> clazz) {
        T t = null;
        try {
            var constructor = clazz.getConstructor();
            t = constructor.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return t;
    }
}
