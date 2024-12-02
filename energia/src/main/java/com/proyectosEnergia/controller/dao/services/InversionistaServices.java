package com.proyectosEnergia.controller.dao.services;

import com.proyectosEnergia.controller.dao.InversionistaDao;
import com.proyectosEnergia.controller.tda.list.LinkedList;
import com.proyectosEnergia.models.Inversionista;
import com.proyectosEnergia.models.Enumerator.TipoIdentificacion;
import com.proyectosEnergia.models.Enumerator.TipoInversionista;

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

    public LinkedList orderInserSort(Integer type_order, String atributo){
        return obj.orderInserSort(type_order, atributo);
    }

    public LinkedList orderMergeSort (Integer type_order, String atributo){
        return obj.orderMergeSort(type_order, atributo);
    }

    public LinkedList<Inversionista> buscar_apellidos(String apellidos){
        return obj.buscar_apellidos(apellidos);
    }

    public Inversionista buscar_identificacion(String identificacion){
        return obj.buscar_identificacion(identificacion);
    }


}
