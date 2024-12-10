package com.proyectosEnergia.controller.dao.services;

import com.proyectosEnergia.controller.dao.ProyectoDao;
import com.proyectosEnergia.controller.tda.list.LinkedList;
import com.proyectosEnergia.models.Enumerator.TipoEnergia;
import com.proyectosEnergia.models.Proyecto;

public class ProyectoServices {
    private ProyectoDao obj;

    public ProyectoServices() {
        obj = new ProyectoDao();
    }

    public Boolean save() throws Exception {
        return obj.save(); 
    }

    public Boolean update() throws Exception {
        return obj.update(); 
    }

    public Boolean delete() throws Exception {
        return obj.delete(); 
    }

    public LinkedList listAll() {
        return obj.getListAll();
    }

    public Proyecto getProyecto() throws Exception {
        return obj.getProyecto();
    }

    public void setProyecto(Proyecto proyecto) {
        obj.setProyecto(proyecto);
    }

    public TipoEnergia getTipoEnergia(String tipo){
        return obj.getTipoEnergia(tipo);
    }

    public TipoEnergia[] getTipos(){
        return obj.getTipos();
    }

    public Proyecto get(Integer id) throws Exception {
        return obj.get(id);
    }
}
