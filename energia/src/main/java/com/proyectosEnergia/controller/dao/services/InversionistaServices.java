package com.proyectosEnergia.controller.dao.services;

import com.proyectosEnergia.controller.dao.InversionistaDao;
import com.proyectosEnergia.controller.tda.list.LinkedList;
import com.proyectosEnergia.models.Enumerator.TipoIdentificacion;
import com.proyectosEnergia.models.Enumerator.TipoInversionista;
import com.proyectosEnergia.models.Inversionista;

public class InversionistaServices {
    private InversionistaDao obj;

    public InversionistaServices() {
        obj = new InversionistaDao();
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

    public Inversionista getInversionista() {
        return obj.getInversionista();
    }

    public void setInversionista(Inversionista inversionista) {
        obj.setInversionista(inversionista);
    }

    public TipoIdentificacion getTipoIdentificacion(String tipo){
        return obj.getTipoIdentificacion(tipo);
    }

    public TipoIdentificacion[] getTipos(){
        return obj.getTipos();
    }

    public TipoInversionista getTipoInversionista(String tipo) {
        return obj.getTipoInversionista(tipo);
    }

    public TipoInversionista[] getTiposInversionista(){
        return obj.getTiposInversionista();
    }

    public Inversionista get(Integer id) throws Exception {
        return obj.get(id);
    }

    
}
