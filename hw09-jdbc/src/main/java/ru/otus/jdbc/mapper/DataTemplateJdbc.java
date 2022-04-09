package ru.otus.jdbc.mapper;


import ru.otus.jdbc.core.repository.DataTemplate;
import ru.otus.jdbc.core.repository.DataTemplateException;
import ru.otus.jdbc.core.repository.executor.DbExecutor;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Сохратяет объект в базу, читает объект из базы
 */
public class DataTemplateJdbc<T> implements DataTemplate<T> {

    private final DbExecutor dbExecutor;
    private final EntitySQLMetaData<T> entitySQLMetaData;
    private final EntityClassMetaData<T> entityClassMetaData;

    public DataTemplateJdbc(DbExecutor dbExecutor, EntitySQLMetaData<T> entitySQLMetaData, EntityClassMetaData<T> entityClassMetaData) {
        this.dbExecutor = dbExecutor;
        this.entitySQLMetaData = entitySQLMetaData;
        this.entityClassMetaData = entityClassMetaData;
    }

    @Override
    public Optional<T> findById(Connection connection, long id) {
        return dbExecutor.executeSelect(connection, entitySQLMetaData.getSelectByIdSql(), List.of(id), rs -> {
            try {
                T entity = null;
                if (rs.next()) {
                    var constructor = entityClassMetaData.getConstructor();
                    try {
                        entity = constructor.newInstance();
                        setFieldsValue(entity, rs);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return entity;
                }
                return null;
            } catch (SQLException e) {
                throw new DataTemplateException(e);
            }
        });
    }

    private T setFieldsValue(T entity, ResultSet rs) {

        for (Field field: entityClassMetaData.getAllFields()) {
            field.setAccessible(true);
            try {
                if (field.getGenericType().equals(Long.class)) {
                    field.set(entity, rs.getLong(field.getName()));
                    continue;
                }
                field.set(entity, rs.getString(field.getName()));
            }catch (Exception ex) {}
        }
        return entity;
    }

    @Override
    public List<T> findAll(Connection connection) {
        throw new UnsupportedOperationException();
    }

    @Override
    public long insert(Connection connection, T client) {
        try {
            return dbExecutor.executeStatement(connection, entitySQLMetaData.getInsertSql(), getInsertValues(client));
        } catch (Exception e) {
            throw new DataTemplateException(e);
        }
    }

    @Override
    public void update(Connection connection, T client) {
        try {
            dbExecutor.executeStatement(connection, entitySQLMetaData.getUpdateSql(), getUpdateFields(client));
        } catch (Exception e) {
            throw new DataTemplateException(e);
        }
    }

    private List<Object> getUpdateFields(T entity) {
        return entityClassMetaData.getFieldsWithoutId()
                                  .stream()
                                  .peek(field -> field.setAccessible(true))
                                  .map(field -> {
                                      try {
                                          return field.get(entity);
                                      } catch (IllegalAccessException e) {
                                          e.printStackTrace();
                                      }
                                      return new Object();
                                  })
                                  .filter(Objects::nonNull)
                                  .collect(Collectors.toList());
    }

    private List<Object> getInsertValues(T entity) {
        return entityClassMetaData.getAllFields()
                                  .stream()
                                  .peek(field -> field.setAccessible(true))
                                  .map(field -> {
                                      try {
                                          return field.get(entity);
                                      } catch (IllegalAccessException e) {
                                          e.printStackTrace();
                                      }
                                      return new Object();
                                  })
                                  .filter(Objects::nonNull)
                                  .collect(Collectors.toList());
    }
}
