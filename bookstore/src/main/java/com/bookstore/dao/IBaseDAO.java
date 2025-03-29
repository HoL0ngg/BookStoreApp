package com.bookstore.dao;

import java.util.List;

public interface IBaseDAO<T> {
    int insert(T t);

    int update(T t);

    int delete(int id);

    List<T> selectAll();

    T selectById(int id);
}