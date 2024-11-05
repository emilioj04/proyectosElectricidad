package com.proyectosEnergia.controller.dao.services;

import com.proyectosEnergia.controller.dao.InversionDao;
import com.proyectosEnergia.controller.tda.list.LinkedList;
import com.proyectosEnergia.models.Inversion;
import com.google.gson.Gson;

public class InversionServices {
    private InversionDao obj;

    public InversionServices() {
        obj = new InversionDao();
    }

    public Boolean save(Inversion inversion) throws Exception {
        obj.persist(inversion);
        return true; 
    }

    public LinkedList<Inversion> listAll() {
        return obj.listAll();
    }

    public Inversion getInversion(Integer id) throws Exception {
        return obj.get(id);
    }

    public void setInversion(Inversion inversion) {
        obj.setInversion(inversion);
    }

    public String toJson() {
        LinkedList<Inversion> inversiones = obj.listAll();
        return new Gson().toJson(inversiones.toArray()); 
    }

    public String getInversionJsonById(Integer id) throws Exception {
        Inversion inversion = obj.get(id);
        return new Gson().toJson(inversion); 
    }

    public void updateInversion(Inversion inversion, Integer index) throws Exception {
        obj.merge(inversion, index); 
    }

    public void deleteInversion(Integer id) throws Exception {
        obj.delete(id); 
    }
}
