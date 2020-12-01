package com.lexsoft.project.beer.database.repository;

public interface DaoMethodProviderInterface<T> {

    T create(T t);

    T find(Object id);

    T update(T t);

    Object delete(Object id);

}
