package ru.job4j.hiber.persistence;

import java.util.List;

public interface GenericDao<T> {

    void saveOrUpdate(T entity);

    void delete(T entity);

    T read(long id) throws PersistException;

    List<T> readUndone() throws PersistException;

    List<T> readAll() throws PersistException;
}
