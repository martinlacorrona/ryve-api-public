package com.martinlacorrona.ryve.api.service;

import com.martinlacorrona.ryve.api.model.FavouriteStationServiceModel;

import java.util.List;

public interface UserFavouriteStationService {
    /**
     * Favoritos del usuario
     * @return las estaciones favoritas del usuario
     */
    List<FavouriteStationServiceModel> getFavourites();

    /**
     * Añade una estacion favorita por id
     * @param idStationService id de la estacion
     * @return las estaciones favoritas del usuario
     */
    List<FavouriteStationServiceModel> addFavourite(Long idStationService);

    /**
     * Borra una estacion favorita por id
     * @param idStationService id de la estacion
     * @return las estaciones favoritas del usuario
     */
    List<FavouriteStationServiceModel> removeFavourite(Long idStationService);
}
