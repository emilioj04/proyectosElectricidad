package com.proyectosEnergia.models;

public class Inversionista {
    private String nombre;
    private String tipoInversionista;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipoInversionista() {
        return tipoInversionista;
    }

    public void setTipoInversionista(String tipoInversionista) {
        this.tipoInversionista = tipoInversionista;
    }

    public Inversionista(String nombre, String tipoInversionista) {
        this.nombre = nombre;
        this.tipoInversionista = tipoInversionista;
    }

}

