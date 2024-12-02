package com.proyectosEnergia.models.Enumerator;

public enum TipoInversionista {
    SIMPLE("Simple"),
    ANGEL("Angel"),
    SEMILLA("Semilla"),
    CORPORATIVO("Corporativo");

    private String name;

    TipoInversionista(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
