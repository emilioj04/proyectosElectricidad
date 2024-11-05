package com.proyectosEnergia.controller.dao;

import com.proyectosEnergia.controller.dao.implement.InterfazDao;
import com.proyectosEnergia.controller.dao.implement.AdapterDao;
import com.proyectosEnergia.controller.tda.arreglos.Arreglo;
import com.proyectosEnergia.models.Inversion;

public class InversionDao implements InterfazDao<Inversion> {
    private Arreglo<Inversion> inversiones; 
    private AdapterDao<Inversion> adapterDao;


    public InversionDao() {
        this.inversiones = new Arreglo<>(); // Inicialización del arreglo
        this.adapterDao = new AdapterDao<>(Inversion.class);
        this.inversiones = adapterDao.getAll();
    }


    public void setInversion(Inversion inversion) {
        inversiones.add(inversion); // Agregar nueva inversión
    }

    @Override
    public Arreglo<Inversion> getAll() {
        return inversiones; 
    }

    @Override
    public void persist(Inversion inversion) throws Exception {
        inversiones.add(inversion); 
        adapterDao.persist(inversion); 
    }

    @Override
    public void merge(Inversion inversion, Integer index) throws Exception {
        inversiones.update(inversion, index); 
        adapterDao.merge(inversion, index); 
    }

    @Override
    public Inversion get(Integer id) throws Exception {
        return inversiones.get(id - 1); 
    }

    @Override
    public void delete(Integer id) throws Exception {
        inversiones.delete(id - 1); 
        adapterDao.delete(id);
    }

}
