package com.devsuperior.bds04.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.io.Serializable;
import java.util.List;

public interface ServiceInterface<T, L extends Serializable> {

    T save(T dto);

    T update(L id, T dto);

    void delete(L id);

    Page<T> findAll(Pageable pageable);

    T findById(L id);
}
