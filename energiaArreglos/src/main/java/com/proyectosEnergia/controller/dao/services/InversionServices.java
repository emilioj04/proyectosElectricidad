package com.proyectosEnergia.controller.dao.services;

import com.google.gson.Gson;
import com.proyectosEnergia.controller.dao.InversionDao;
import com.proyectosEnergia.controller.tda.arreglos.Arreglo;
import com.proyectosEnergia.models.Inversion;

public class InversionServices {
    private InversionDao obj;

    public InversionServices() {
        obj = new InversionDao();
    }

    public Boolean save(Inversion inversion) throws Exception {
        obj.persist(inversion);
        return true;
    }

    public Arreglo<Inversion> getAll() {
        return obj.getAll();
    }

    public Inversion getInversion(Integer id) throws Exception {
        return obj.get(id);
    }

    public void setInversion(Inversion inversion) {
        obj.setInversion(inversion);
    }

    public String toJson() {
        Arreglo<Inversion> inversiones = obj.getAll();
        return new Gson().toJson(inversiones);
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
