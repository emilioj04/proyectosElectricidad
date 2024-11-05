package com.proyectosEnergia.controller.dao;

import com.proyectosEnergia.controller.dao.implement.InterfazDao;
import com.proyectosEnergia.controller.dao.implement.AdapterDao;
import com.proyectosEnergia.controller.tda.list.LinkedList;
import com.proyectosEnergia.models.Inversionista;

public class InversionistaDao implements InterfazDao<Inversionista> {
    private LinkedList<Inversionista> inversionistas;
    private AdapterDao<Inversionista> adapterDao;

    public InversionistaDao() {
        this.inversionistas = new LinkedList<>();
        this.adapterDao = new AdapterDao<>(Inversionista.class);
        this.inversionistas = adapterDao.listAll(); 
    }

    public void setInversionista(Inversionista inversionista) {
        inversionistas.add(inversionista);
    }

    @Override
    public LinkedList<Inversionista> listAll() {
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
        inversionistas.delete(id);
        adapterDao.delete(id); 
    }
}
