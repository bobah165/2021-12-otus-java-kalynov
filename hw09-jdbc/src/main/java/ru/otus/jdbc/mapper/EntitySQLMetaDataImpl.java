package ru.otus.jdbc.mapper;

import java.lang.reflect.Field;
import java.util.stream.Collectors;

public class EntitySQLMetaDataImpl<T> implements EntitySQLMetaData<T> {
    private final EntityClassMetaData<T> entityClassMetaData;

    public EntitySQLMetaDataImpl(EntityClassMetaData<T> entityClassMetaData) {
        this.entityClassMetaData = entityClassMetaData;
    }

    @Override
    public String getSelectAllSql() {
        var tableName = getTableName();
        return "select * from " + tableName;
    }

    @Override
    public String getSelectByIdSql() {
        var tableName = getTableName();
        var idFieldName = entityClassMetaData.getIdField().getName();
        var allFieldsName = entityClassMetaData.getAllFields().stream().map(Field::getName)
                .collect(Collectors.joining(", "));
        return "select " + allFieldsName + " from " + tableName + " where " + idFieldName + "=?";
    }

    @Override
    public String getInsertSql() {
        var tableName = getTableName();
        var idFieldName = entityClassMetaData.getIdField().getName();
        var otherFieldNames = getOtherFieldNames();
        var entityValues = readValues();

        return "insert into " + tableName + " (" + otherFieldNames + ") values " + "(" + entityValues + ")";
    }

    @Override
    public String getUpdateSql() {
        var tableName = getTableName();
        var idFieldName = entityClassMetaData.getIdField().getName();
        var updateFields = getUpdateFields();

        return "update " + tableName + " set " + updateFields + " where " + idFieldName + "= ?";
    }

    private String getTableName() {
        var arrayOfPackageNames = entityClassMetaData.getName().split("\\.");
        return arrayOfPackageNames[arrayOfPackageNames.length - 1].toLowerCase();
    }

    private String getUpdateFields() {
        return entityClassMetaData.getFieldsWithoutId()
                                  .stream()
                                  .map(field -> field.getName() + "= ? ")
                                  .collect(Collectors.joining(", "));
    }


    private Long getIdValue(T entity) {
        var idField = entityClassMetaData.getIdField();
        idField.setAccessible(true);
        try {
            return (Long) idField.get(entity);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return -1L;
    }


    private String getOtherFieldNames() {
        return entityClassMetaData.getFieldsWithoutId()
                                  .stream()
                                  .map(Field::getName)
                                  .collect(Collectors.joining(", "));
    }

    private String readValues() {
        return entityClassMetaData.getFieldsWithoutId()
                                  .stream()
                                  .map(field -> " ? ")
                                  .collect(Collectors.joining(", "));
    }

//    private String readValues(T entity) {
//        return entityClassMetaData.getAllFields()
//                                  .stream()
//                                  .peek(field -> field.setAccessible(true))
//                                  .map(field -> {
//                                      try {
//                                          return field.get(entity).toString();
//                                      } catch (IllegalAccessException e) {
//                                          e.printStackTrace();
//                                      }
//                                      return "";
//                                  })
//                                  .collect(Collectors.joining(", "));
//    }
}
