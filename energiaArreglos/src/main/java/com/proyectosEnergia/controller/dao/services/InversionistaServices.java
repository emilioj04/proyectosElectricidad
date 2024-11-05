package com.proyectosEnergia.controller.dao.services;

import com.google.gson.Gson;
import com.proyectosEnergia.controller.dao.InversionistaDao;
import com.proyectosEnergia.controller.tda.arreglos.Arreglo;
import com.proyectosEnergia.models.Inversionista;

public class InversionistaServices {
    private InversionistaDao obj;

     public InversionistaServices() {
        obj = new InversionistaDao();
    }

    public Boolean save(Inversionista inversionista) throws Exception {
        obj.persist(inversionista);
        return true; 
    }

    public Arreglo<Inversionista> getAll() {
        return obj.getAll();
    }

    public Inversionista getInversionista(Integer id) throws Exception {
        return obj.get(id);
    }

    public void setInversionista(Inversionista inversionista) {
        obj.setInversionista(inversionista);
    }

    public String toJson() {
        Arreglo<Inversionista> inversionistas = obj.getAll();
        return new Gson().toJson(inversionistas);
    }

    public String getInversionistaJsonById(Integer id) throws Exception {
        Inversionista inversionista = obj.get(id);
        return new Gson().toJson(inversionista); 
    }

    public void updateInversionista(Inversionista inversionista, Integer index) throws Exception {
        obj.merge(inversionista, index); 
    }

    public void deleteInversionista(Integer id) throws Exception {
        obj.delete(id); 
    }
}
