package com.proyectosEnergia.models.Enumerator;

public enum TipoEnergia {
    EOLICA("Eolica"),
    SOLAR("Solar"),
    HIDRAULICA("Hidraulica"),
    GEOTERMICA("Geotermica"),
    NUCLEAR("Nuclear"),
    CARBON("Carbon"),
    GAS("Gas"),
    PETROLEO("Petroleo"),
    HIDROGENO("Hidrogeno");

    private String name;

    TipoEnergia(String name) {
        this.name = name;
    }   

    public String getName() {
        return name;
    }

}
