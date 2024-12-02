package com.proyectosEnergia.models;

import com.proyectosEnergia.models.Enumerator.TipoIdentificacion;
import com.proyectosEnergia.models.Enumerator.TipoInversionista;

public class Inversionista {
    private Integer id;
    private String identificacion; 
    private TipoIdentificacion tipo;
    private String nombre;
    private String apellido;
    private TipoInversionista tipoInversionista;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
    }

    public TipoIdentificacion getTipoIdentificacion() {
        return tipo;
    }

    public void setTipoIdentificacion(TipoIdentificacion tipoIdentificacion) {
        this.tipo = tipoIdentificacion;
    }
    
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public TipoInversionista getTipoInversionista() {
        return tipoInversionista;
    }

    public void setTipoInversionista(TipoInversionista tipoInversionista) {
        this.tipoInversionista = tipoInversionista;
    }
}

