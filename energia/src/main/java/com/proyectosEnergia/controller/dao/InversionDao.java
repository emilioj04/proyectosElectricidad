package com.proyectosEnergia.controller.dao;

import com.proyectosEnergia.controller.dao.implement.InterfazDao;
import com.proyectosEnergia.controller.dao.implement.AdapterDao;
import com.proyectosEnergia.controller.tda.list.LinkedList;
import com.proyectosEnergia.models.Inversion;

public class InversionDao extends AdapterDao<Inversion> {
    private Inversion inversion;
    private LinkedList listAll;

    public InversionDao() {
        super(Inversion.class);
        this.listAll = this.listAll();
    }

    public Inversion getInversion() {
        if (inversion == null) {
            inversion = new Inversion();
        }   
        return inversion;
    }

    public void setInversion(Inversion inversion) {
        this.inversion = inversion;
    }

    public LinkedList getListAll() {
        return listAll;
    }

    public Boolean save() throws Exception {
        Integer id = getListAll().getSize() + 1;
        inversion.setId(id);
        this.persist(this.inversion);
        this.listAll = this.listAll();
        return true;
    }

    public Boolean update() throws Exception {
        this.merge(getInversion(), getInversion().getId()-1);
        this.listAll = this.listAll();
        return true;
    }

    public Boolean delete() throws Exception {
        this.delete(getInversion().getId());
        this.listAll = this.listAll();
        return true;
    }

    
}
