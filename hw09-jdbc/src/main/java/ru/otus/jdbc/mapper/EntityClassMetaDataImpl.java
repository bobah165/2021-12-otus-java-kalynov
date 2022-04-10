package ru.otus.jdbc.mapper;

import ru.otus.jdbc.annotation.Id;
import ru.otus.jdbc.exception.NoIdFieldException;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class EntityClassMetaDataImpl<T> implements EntityClassMetaData<T> {
    private final Class<T> entity;

    public EntityClassMetaDataImpl(Class<T> clazz) {
        this.entity = clazz;
    }

    @Override
    public String getName() {
        return entity.getName();
    }

    @Override
    public Constructor<T> getConstructor() {
        Constructor<T> constructor = null;
        try {
            constructor = entity.getConstructor();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return constructor;
    }

    @Override
    public Field getIdField() {
        return Arrays.stream(entity.getDeclaredFields())
                     .filter(method -> method.isAnnotationPresent(Id.class))
                     .findFirst()
                     .orElseThrow(NoIdFieldException::new);
    }


    @Override
    public List<Field> getAllFields() {
        return Arrays.stream(entity.getDeclaredFields())
                     .collect(Collectors.toList());
    }

    @Override
    public List<Field> getFieldsWithoutId() {
        return getAllFields().stream()
                             .filter(field -> !field.isAnnotationPresent(Id.class))
                             .collect(Collectors.toList());
    }
}
