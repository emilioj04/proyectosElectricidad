package com.proyectosEnergia.controller.dao;

import com.proyectosEnergia.controller.dao.implement.InterfazDao;
import com.proyectosEnergia.controller.dao.implement.AdapterDao;
import com.proyectosEnergia.controller.tda.arreglos.Arreglo;
import com.proyectosEnergia.models.Proyecto;

public class ProyectoDao implements InterfazDao<Proyecto> {
    private Arreglo<Proyecto> proyectos;
    private AdapterDao<Proyecto> adapterDao;

    public ProyectoDao() {
        this.proyectos = new Arreglo<>(); // Inicialización del arreglo
        this.adapterDao = new AdapterDao<>(Proyecto.class);
        this.proyectos = adapterDao.getAll();
    }

    public void setProyecto(Proyecto proyecto) {
        proyectos.add(proyecto); 
    }

    @Override
    public Arreglo<Proyecto> getAll() {
        return proyectos;
    }

    @Override
    public void persist(Proyecto proyecto) throws Exception {
        proyectos.add(proyecto); 
        adapterDao.persist(proyecto); 
    }

    @Override
    public void merge(Proyecto proyecto, Integer index) throws Exception {
        proyectos.update(proyecto, index);
        adapterDao.merge(proyecto, index);
    }

    @Override
    public Proyecto get(Integer id) throws Exception {
        return proyectos.get(id - 1);
    }

    @Override
    public void delete(Integer id) throws Exception {
        proyectos.delete(id - 1);
        adapterDao.delete(id);
    }
}
