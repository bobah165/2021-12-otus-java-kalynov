package ru.otus.homework14.dao.sessionmanager;


public interface TransactionManager {

    <T> T doInTransaction(TransactionAction<T> action);
    <T> T doInReadOnlyTransaction(TransactionAction<T> action);
}
