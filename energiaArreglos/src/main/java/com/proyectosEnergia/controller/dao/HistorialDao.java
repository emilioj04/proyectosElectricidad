package com.proyectosEnergia.controller.dao;

import com.proyectosEnergia.controller.dao.implement.InterfazDao;
import com.proyectosEnergia.controller.dao.implement.AdapterDao;
import com.proyectosEnergia.controller.tda.arreglos.Arreglo;
import com.proyectosEnergia.models.Historial;

public class HistorialDao implements InterfazDao<Historial> {
    private Arreglo<Historial> historiales; 
    private AdapterDao<Historial> adapterDao;

    public HistorialDao() {
        this.historiales = new Arreglo<>(); // Inicialización del arreglo
        this.adapterDao = new AdapterDao<>(Historial.class);
        this.historiales = adapterDao.getAll();
    }

    @Override
    public Arreglo<Historial> getAll() {
        return historiales; 
    }

    @Override
    public void persist(Historial historial) throws Exception {
        historiales.add(historial); 
        adapterDao.persist(historial); 
    }

    @Override
    public void merge(Historial historial, Integer index) throws Exception {
        historiales.update(historial, index); 
        adapterDao.merge(historial, index); 
    }

    @Override
    public Historial get(Integer id) throws Exception {
        return historiales.get(id); 
    }

    @Override
    public void delete(Integer id) throws Exception {
        historiales.delete(id); 
        adapterDao.delete(id);
    }
    public int getNextId() {
        return historiales.getSize() + 1; 
    }
}