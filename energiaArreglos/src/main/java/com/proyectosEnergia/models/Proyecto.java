package com.proyectosEnergia.models;

public class Proyecto {
    private String nombre;
    private String tipoEnergia;
    private int tiempoConstruccion;
    private int tiempoVida;
    private int inversion;
    private double capacidadGeneracionDiaria;
    private double costoGeneracionDiaria;


    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipoEnergia() {
        return tipoEnergia;
    }

    public void setTipoEnergia(String tipoEnergia) {
        this.tipoEnergia = tipoEnergia;
    }

    public int getTiempoConstruccion() {
        return tiempoConstruccion;
    }   

    public void setTiempoConstruccion(int tiempoConstruccion) {
        this.tiempoConstruccion = tiempoConstruccion;
    }

    public int getTiempoVida() {
        return tiempoVida;
    }

    public void setTiempoVida(int tiempoVida) {
        this.tiempoVida = tiempoVida;
    }

    public int getInversion() {
        return inversion;
    }

    public void setInversion(int inversion) {
        this.inversion = inversion;
    }

    public double getCapacidadGeneracionDiaria() {
        return capacidadGeneracionDiaria;
    }

    public void setCapacidadGeneracionDiaria(double capacidadGeneracionDiaria) {
        this.capacidadGeneracionDiaria = capacidadGeneracionDiaria;
    }

    public double getCostoGeneracionDiaria() {
        return costoGeneracionDiaria;
    }

    public void setCostoGeneracionDiaria(double costoGeneracionDiaria) {
        this.costoGeneracionDiaria = costoGeneracionDiaria;
    }

    public Proyecto(String nombre, String tipoEnergia, int tiempoConstruccion, int tiempoVida, int inversion, double capacidadGeneracionDiaria, double costoGeneracionDiaria) {
        this.nombre = nombre;
        this.tipoEnergia = tipoEnergia;
        this.tiempoConstruccion = tiempoConstruccion;
        this.tiempoVida = tiempoVida;
        this.inversion = inversion;
        this.capacidadGeneracionDiaria = capacidadGeneracionDiaria;
        this.costoGeneracionDiaria = costoGeneracionDiaria;
    }
}