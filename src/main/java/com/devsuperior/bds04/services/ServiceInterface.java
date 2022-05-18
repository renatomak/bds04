package com.devsuperior.bds04.services;

import java.io.Serializable;
import java.util.List;

public interface ServiceInterface<T, L extends Serializable> {

    T save(T dto);

    T update(T dto);

    void delete(L id);

    List<T> list();

    T findById(L id);
}
