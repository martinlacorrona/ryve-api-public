package com.martinlacorrona.ryve.api.service;

import com.martinlacorrona.ryve.api.model.UserPreferencesModel;

public interface UserPreferencesService {
    /**
     * Las preferencias del usuario
     * @return un modelo de las preferencias del usuario
     */
    UserPreferencesModel get();

    /**
     * Actualiza las actuales
     * @param userPreferencesModel pasandole un modelo
     * @return devuelve las preferencias actulizadas
     */
    UserPreferencesModel update(UserPreferencesModel userPreferencesModel);
}
