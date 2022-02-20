package com.martinlacorrona.ryve.api.service;

import com.martinlacorrona.ryve.api.model.DirectionsModel;

public interface DirectionsService {
    /**
     * Requerir la ruta, usaremos del objeto directions response el routes[0].overviewPolyline
     *
     * @param origin      coordenadas de origen en formato ("lat,lng")
     * @param destination coordenadas de destino en formato ("lat,lng")
     * @param avoid       si queremso evitar peajos ponemos ("tolls") y si no nada
     * @return el objeto de respuesta de la API
     */
    DirectionsModel requestRouteByOriginDestinationAndAvoidTolls(
            String origin, String destination, Boolean avoid);
}
