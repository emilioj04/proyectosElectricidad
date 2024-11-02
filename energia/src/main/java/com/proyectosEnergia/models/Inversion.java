package com.proyectosEnergia.models;

public class Inversion {
    private Proyecto proyecto;
    private Inversionista inversionista;
    private double montoInvertido;


    public Proyecto getProyecto() {
        return proyecto;
    }

    public void setProyecto(Proyecto proyecto) {
        this.proyecto = proyecto;
    }

    public Inversionista getInversionista() {
        return inversionista;
    }

    public void setInversionista(Inversionista inversionista) {
        this.inversionista = inversionista;
    }

    public double getMontoInvertido() {
        return montoInvertido;
    }

    public void setMontoInvertido(double montoInvertido) {
        this.montoInvertido = montoInvertido;
    }


    public Inversion(Proyecto proyecto, Inversionista inversionista, double montoInvertido) {
        this.proyecto = proyecto;
        this.inversionista = inversionista;
        this.montoInvertido = montoInvertido;
    }

}
