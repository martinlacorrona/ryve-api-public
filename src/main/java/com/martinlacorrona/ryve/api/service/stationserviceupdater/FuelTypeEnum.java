package com.martinlacorrona.ryve.api.service.stationserviceupdater;

public enum FuelTypeEnum {
    BIODIESEL(1L), BIOETANOL(2L), GAS_NATURAL_COMPRIMIDO(3L), GAS_NATURAL_LICUADO(4L),
    GASES_LICUADOS_DEL_PETROLEO(5L), GASOLEO_A(6L), GASOLEO_B(7L), GASOLEO_PREMIUM(8L),
    GASOLINA_95_E10(9L), GASOLINA_95_E5(10L), GASOLINA_95_E5_PREMIUM(11L),
    GASOLINA_98_E10(12L), GASOLINA_98_E5(13L), HIDROGENO(14L);

    private Long value;

    private FuelTypeEnum(Long value) {
        this.value = value;
    }

    public Long getValue() {
        return value;
    }
}
