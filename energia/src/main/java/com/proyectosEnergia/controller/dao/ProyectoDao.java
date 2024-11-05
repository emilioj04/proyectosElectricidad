package com.proyectosEnergia.controller.dao;

import com.proyectosEnergia.controller.dao.implement.InterfazDao;
import com.proyectosEnergia.controller.dao.implement.AdapterDao;
import com.proyectosEnergia.controller.tda.list.LinkedList;
import com.proyectosEnergia.models.Proyecto;


public class ProyectoDao implements InterfazDao<Proyecto> {
    private LinkedList<Proyecto> proyectos;
    private AdapterDao<Proyecto> adapterDao;

    public ProyectoDao() {
        this.proyectos = new LinkedList<>();
        this.adapterDao = new AdapterDao<>(Proyecto.class);
        this.proyectos = adapterDao.listAll(); 
    }

    public void setProyecto(Proyecto proyecto) {
        proyectos.add(proyecto);
    }


    @Override
    public LinkedList<Proyecto> listAll() {
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
        proyectos.delete(id);
        adapterDao.delete(id); 
    }
}
