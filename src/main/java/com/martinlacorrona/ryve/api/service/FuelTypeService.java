package com.martinlacorrona.ryve.api.service;

import com.martinlacorrona.ryve.api.model.FuelTypeModel;

import java.util.List;

public interface FuelTypeService {
    /**
     * Devuelve todos los tipos de combustible
     * @return tipos de combustible
     */
    List<FuelTypeModel> getAll();

    /**
     * Devuelve todos los tipos de combustible que esten activados
     * @return tipos de combustible
     */
    List<FuelTypeModel> getEnabledFuelTypes();
}
