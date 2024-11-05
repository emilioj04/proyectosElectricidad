package com.proyectosEnergia.controller.dao.implement;

import com.proyectosEnergia.controller.tda.list.LinkedList;

public interface InterfazDao<T> {
    public void persist(T object) throws Exception;
    public void merge(T objcet, Integer index) throws Exception;
    public void delete(Integer id) throws Exception;
    public LinkedList<T> listAll();
    public T get(Integer id) throws Exception;
} 
