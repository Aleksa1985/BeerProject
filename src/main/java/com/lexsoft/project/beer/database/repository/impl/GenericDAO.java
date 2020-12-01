package com.lexsoft.project.beer.database.repository.impl;

import com.lexsoft.project.beer.database.repository.DaoMethodProviderInterface;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public abstract class GenericDAO<T> implements DaoMethodProviderInterface<T> {

    @PersistenceContext
    protected EntityManager em;

    protected Class<T> type;

    public GenericDAO() {
        Type t = getClass().getGenericSuperclass();
        ParameterizedType pt = (ParameterizedType) t;
        type = (Class) pt.getActualTypeArguments()[0];
    }

    @Override
    public T create(final T t) {
        this.em.persist(t);
        return t;
    }

    @Override
    public Object delete(Object id) {
        this.em.remove(id);
        return id;
    }

    @Override
    public T find(final Object id) {
        return (T) this.em.find(type, id);
    }

    @Override
    public T update(final T t) {
        return this.em.merge(t);
    }

    public void checkConnection() {
        this.em.createNativeQuery("select 1 from DUAL");
    }

}
