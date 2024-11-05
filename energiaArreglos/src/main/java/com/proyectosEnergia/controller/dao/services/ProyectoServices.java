package com.proyectosEnergia.controller.dao.services;

import com.google.gson.Gson;
import com.proyectosEnergia.controller.dao.ProyectoDao;
import com.proyectosEnergia.controller.tda.arreglos.Arreglo;
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

    public Arreglo<Proyecto> getAll() {
        return obj.getAll();
    }

    public Proyecto getProyecto(Integer id) throws Exception {
        return obj.get(id);
    }

    public void setProyecto(Proyecto proyecto) {
        obj.setProyecto(proyecto);
    }

    public String toJson() {
        Arreglo<Proyecto> proyectos = obj.getAll();
        return new Gson().toJson(proyectos);
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
