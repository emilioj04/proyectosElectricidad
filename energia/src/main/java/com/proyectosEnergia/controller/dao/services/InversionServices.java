package com.proyectosEnergia.controller.dao.services;

import com.proyectosEnergia.controller.dao.InversionDao;
import com.proyectosEnergia.controller.tda.list.LinkedList;
import com.proyectosEnergia.models.Inversion;
import com.proyectosEnergia.models.Inversionista;
import com.proyectosEnergia.models.Proyecto;

import java.util.HashMap;

import com.google.gson.Gson;

public class InversionServices {
    private InversionDao obj;

    public Object[] listShowAll() throws Exception {
        if (obj.getListAll().isEmpty()) {
            Inversion[] lista = (Inversion[]) obj.getListAll().toArray();
            Object[] respuesta = new Object[lista.length];
            for(int i = 0; i < lista.length; i++){
                Inversionista inv = new InversionistaServices().get(lista[i].getIdInversionista());
                Proyecto pro = new ProyectoServices().get(lista[i].getIdProyecto());
                HashMap mapa = new HashMap();
                mapa.put("id", lista[i].getId());
                mapa.put("montoInvertido", lista[i].getMontoInvertido());
                mapa.put("fecha", lista[i].getFecha());
                mapa.put("investor", inv);
                mapa.put("project", pro);
                respuesta[i] = mapa;
            }
            return respuesta;
        }
        return new Object[]{};
    }

    public InversionServices() {
        obj = new InversionDao();
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

    public Inversion getInversion() {
        return obj.getInversion();
    }

    public void setInversion(Inversion Inversion) {
        obj.setInversion(Inversion);
    }
       
    public Inversion get(Integer id) throws Exception {
        return obj.get(id);
    }
/* 
    public LinkedList order(Integer type_order, String atributo) {
        return obj.order(type_order, atributo);
    }
*/
    public LinkedList order_object(Integer type, String atributo) throws Exception {
        return obj.listAll().order(atributo, type);
    }




}
