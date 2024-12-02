package com.proyectosEnergia.models;

import com.proyectosEnergia.models.Enumerator.TipoEnergia;

public class Proyecto {
    private Integer id;
    private String nombre;
    private TipoEnergia tipoEnergia;
    private Integer tiempoConstruccion;
    private Integer tiempoVida;
    private Double inversionTotal;
    private Double capacidadGeneracionDiaria;
    private Double costoGeneracionDiaria;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public TipoEnergia getTipoEnergia() {
        return tipoEnergia;
    }

    public void setTipoEnergia(TipoEnergia tipoEnergia) {
        this.tipoEnergia = tipoEnergia;
    }

    public int getTiempoConstruccion() {
        return tiempoConstruccion;
    }   

    public void setTiempoConstruccion(int tiempoConstruccion) {
        this.tiempoConstruccion = tiempoConstruccion;
    }

    public Integer getTiempoVida() {
        return tiempoVida;
    }

    public void setTiempoVida(int tiempoVida) {
        this.tiempoVida = tiempoVida;
    }

    public double getInversionTotal() {
        return inversionTotal;
    }

    public void setInversionTotal(Double inversionTotal) {
        this.inversionTotal = inversionTotal;
    }

    public double getCapacidadGeneracionDiaria() {
        return capacidadGeneracionDiaria;
    }

    public void setCapacidadGeneracionDiaria(Double capacidadGeneracionDiaria) {
        this.capacidadGeneracionDiaria = capacidadGeneracionDiaria;
    }

    public double getCostoGeneracionDiaria() {
        return costoGeneracionDiaria;
    }

    public void setCostoGeneracionDiaria(Double costoGeneracionDiaria) {
        this.costoGeneracionDiaria = costoGeneracionDiaria;
    }

}

