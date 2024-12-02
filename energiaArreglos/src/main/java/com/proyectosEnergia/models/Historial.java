package com.proyectosEnergia.models;

public class Historial {
    private int id;
    private String tipoOperacion; 
    private String fecha; 
    private String objeto; 



    public Historial(int id, String tipoOperacion, String fecha, String objeto) {
        this.id = id;
        this.tipoOperacion = tipoOperacion;
        this.fecha = fecha;
        this.objeto = objeto;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTipoOperacion() {
        return tipoOperacion;
    }

    public void setTipoOperacion(String tipoOperacion) {
        this.tipoOperacion = tipoOperacion;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getObjeto() {
        return objeto;
    }

    public void setObjeto(String objeto) {
        this.objeto = objeto;
    }
}