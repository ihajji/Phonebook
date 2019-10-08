package com.phonebook.dao.contract;

import com.phonebook.model.exception.NotFoundException;

import java.util.Collection;
import java.util.Optional;

public interface Dao<T> {
    T get(int id) throws NotFoundException;
    Collection<T> getAll() throws NotFoundException;
    int save(T t) throws NotFoundException;
    int update(T t) throws NotFoundException;
    int delete(Integer t) throws NotFoundException;
}
