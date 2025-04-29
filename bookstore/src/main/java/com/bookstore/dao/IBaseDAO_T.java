package com.bookstore.dao;

import java.util.List;

public interface IBaseDAO_T<T> {
    int insert(T t);

    int update(T t);

    int delete(String id);

    List<T> selectAll();

    T selectById(String id);
}
