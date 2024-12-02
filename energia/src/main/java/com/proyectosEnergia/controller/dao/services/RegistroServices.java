package com.proyectosEnergia.controller.dao.services;

import com.google.gson.Gson;
import com.proyectosEnergia.controller.dao.RegistroDao;
import com.proyectosEnergia.controller.tda.list.LinkedList;
import com.proyectosEnergia.models.Registro;

public class RegistroServices {
    private RegistroDao obj;

    public RegistroServices() {
        obj = new RegistroDao();
    }

    public Boolean save() throws Exception {
        return obj.save(); 
    }

    public LinkedList listAll() {
        return obj.getListAll();
    }

    public Registro getRegistro() {
        return obj.getRegistro();
    }

    public void setRegistro(Registro Registro) {
        obj.setRegistro(Registro);
    }


    public Registro get(Integer id) throws Exception {
        return obj.get(id);
    }
}
