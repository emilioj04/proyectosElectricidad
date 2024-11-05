package com.proyectosEnergia.controller.dao.services;

import com.google.gson.Gson;
import com.proyectosEnergia.controller.dao.HistorialDao;
import com.proyectosEnergia.controller.tda.arreglos.Arreglo;
import com.proyectosEnergia.models.Historial;

public class HistorialServices {
    private HistorialDao obj;

    public HistorialServices() {
        obj = new HistorialDao();
    }

    public int getNextId() {
        return obj.getNextId(); 
    }

    public Boolean save(Historial historial) throws Exception {
        obj.persist(historial);
        return true;
    }

    public Arreglo<Historial> getAll() {
        return obj.getAll();
    }

    public Historial getHistorial(Integer id) throws Exception {
        return obj.get(id);
    }

    public String toJson() {
        Arreglo<Historial> historiales = obj.getAll();
        return new Gson().toJson(historiales);
    }

    public void updateHistorial(Historial historial, Integer index) throws Exception {
        obj.merge(historial, index);
    }

    public void deleteHistorial(Integer id) throws Exception {
        obj.delete(id);
    }
}
