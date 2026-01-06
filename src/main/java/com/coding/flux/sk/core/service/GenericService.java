package com.coding.flux.sk.core.service;

import java.util.List;

public interface GenericService<T, U> {

    List<T> findAll();

    T create(U dto);

    T findById(String id);

    T update(String id, U dto);

    void deleteById(String id);

}
