package com.lib.DAO;

import java.io.Serializable;
import java.util.List;

public interface BaseDAO<T, ID extends Serializable> {
    void save(T entity);
    T findById(ID id);
    List<T> findAll();
    void update(T entity);
    void delete(T entity);
}
