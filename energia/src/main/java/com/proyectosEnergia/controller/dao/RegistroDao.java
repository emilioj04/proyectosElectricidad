package com.proyectosEnergia.controller.dao;

import com.proyectosEnergia.controller.dao.implement.AdapterDao;
import com.proyectosEnergia.controller.tda.list.LinkedList;
import com.proyectosEnergia.models.Registro;


public class RegistroDao extends AdapterDao<Registro> {
    private Registro registro;
    private LinkedList listAll;

    public RegistroDao() {
        super(Registro.class);
        this.listAll = this.listAll();
    }

    public Registro getRegistro() {
        if (registro == null) {
            registro = new Registro();
        }
        return registro;
    }

    public void setRegistro(Registro registro) {
        this.registro = registro;
    }

    public LinkedList getListAll() {
        return listAll;
    }

    public Boolean save() throws Exception {
        Integer id = getListAll().getSize() + 1;
        registro.setId(id);
        this.persist(this.registro);
        this.listAll = this.listAll();
        return true;
    }
}
