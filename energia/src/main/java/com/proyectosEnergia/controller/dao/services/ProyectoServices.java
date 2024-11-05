package com.proyectosEnergia.controller.dao.services;

import com.google.gson.Gson;
import com.proyectosEnergia.controller.dao.ProyectoDao;
import com.proyectosEnergia.controller.tda.list.LinkedList;
import com.proyectosEnergia.models.Proyecto;

public class ProyectoServices {
    private ProyectoDao obj;

    public ProyectoServices() {
        obj = new ProyectoDao();
    }

    public Boolean save(Proyecto proyecto) throws Exception {
        obj.persist(proyecto);
        return true; 
    }

    public LinkedList<Proyecto> listAll() {
        return obj.listAll();
    }

    public Proyecto getProyecto(Integer id) throws Exception {
        return obj.get(id);
    }

    public void setProyecto(Proyecto proyecto) {
        obj.setProyecto(proyecto);
    }

    public String toJson() {
        LinkedList<Proyecto> proyectos = obj.listAll();
        return new Gson().toJson(proyectos.toArray()); 
    }

    public String getProyectoJsonById(Integer id) throws Exception {
        Proyecto proyecto = obj.get(id);
        return new Gson().toJson(proyecto); 
    }

    public void updateProyecto(Proyecto proyecto, Integer index) throws Exception {
        obj.merge(proyecto, index); 
    }

    public void deleteProyecto(Integer id) throws Exception {
        obj.delete(id); 
    }
}
