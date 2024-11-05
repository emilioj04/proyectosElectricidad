package com.proyectosEnergia.controller.dao;

import com.proyectosEnergia.controller.dao.implement.InterfazDao;
import com.proyectosEnergia.controller.dao.implement.AdapterDao;
import com.proyectosEnergia.controller.tda.arreglos.Arreglo;
import com.proyectosEnergia.models.Inversionista;

public class InversionistaDao implements InterfazDao<Inversionista> {
    private Arreglo<Inversionista> inversionistas;
    private AdapterDao<Inversionista> adapterDao;

    public InversionistaDao() {
        this.inversionistas = new Arreglo<>(); // Inicialización del arreglo
        this.adapterDao = new AdapterDao<>(Inversionista.class);
        this.inversionistas = adapterDao.getAll();
    }

    public void setInversionista(Inversionista inversionista) {
        inversionistas.add(inversionista); // Agregar nuevo inversionista
    }

    @Override
    public Arreglo<Inversionista> getAll() {
        return inversionistas;
    }

    @Override
    public void persist(Inversionista inversionista) throws Exception {
        inversionistas.add(inversionista); 
        adapterDao.persist(inversionista); 
    }

    @Override
    public void merge(Inversionista inversionista, Integer index) throws Exception {
        inversionistas.update(inversionista, index);
        adapterDao.merge(inversionista, index);
    }

    @Override
    public Inversionista get(Integer id) throws Exception {
        return inversionistas.get(id - 1);
    }

    @Override
    public void delete(Integer id) throws Exception {
        inversionistas.delete(id - 1);
        adapterDao.delete(id);
    }
}

