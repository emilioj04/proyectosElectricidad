package com.proyectosEnergia.models;

public class Inversion {
    private Integer id;
    private Double montoInvertido;
    private Integer idProyecto;
    private Integer idInversionista;
    private String fecha;
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    
    public Integer getIdProyecto() {
        return idProyecto;
    }

    public void setIdProyecto(Integer idProyecto) {
        this.idProyecto = idProyecto;
    }

    public Integer getIdInversionista() {
        return idInversionista;
    }

    public void setIdInversionista(Integer idInversionista) {
        this.idInversionista = idInversionista;
    }

    public double getMontoInvertido() {
        return montoInvertido;
    }

    public void setMontoInvertido(Double montoInvertido) {
        this.montoInvertido = montoInvertido;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
}
