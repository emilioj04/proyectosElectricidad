package com.proyectosEnergia.models.Enumerator;

public enum TipoIdentificacion {
    CEDULA("Cedula"), 
    RUC ("RUC"),
    PASAPORTE("Pasaporte");
    private String name;

    TipoIdentificacion(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
