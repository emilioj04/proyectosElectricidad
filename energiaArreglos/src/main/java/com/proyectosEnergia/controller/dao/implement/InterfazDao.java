package com.proyectosEnergia.controller.dao.implement;

import com.proyectosEnergia.controller.tda.arreglos.Arreglo;

public interface InterfazDao<T> {
    public void persist(T objeto) throws Exception;
    public void merge(T objeto, Integer index) throws Exception;
    public void delete(Integer id) throws Exception;
    public Arreglo<T> getAll(); 
    public T get(Integer id) throws Exception;
}
