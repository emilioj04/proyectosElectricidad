package com.proyectosEnergia.models;

public class Proyecto {
    private String nombre;
    private String tipoEnergia;
    private int tiempoVida;
    private double capacidadGeneracionDiaria;


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

    public int getTiempoVida() {
        return tiempoVida;
    }

    public void setTiempoVida(int tiempoVida) {
        this.tiempoVida = tiempoVida;
    }

    public double getCapacidadGeneracionDiaria() {
        return capacidadGeneracionDiaria;
    }

    public void setCapacidadGeneracionDiaria(double capacidadGeneracionDiaria) {
        this.capacidadGeneracionDiaria = capacidadGeneracionDiaria;
    }

    public Proyecto( String nombre, String tipoEnergia, int tiempoVida, double capacidadGeneracionDiaria) {
        this.nombre = nombre;
        this.tipoEnergia = tipoEnergia;
        this.tiempoVida = tiempoVida;
        this.capacidadGeneracionDiaria = capacidadGeneracionDiaria;
    }
  
}

