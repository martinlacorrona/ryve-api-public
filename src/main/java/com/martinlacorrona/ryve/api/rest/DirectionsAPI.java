package com.martinlacorrona.ryve.api.rest;

import com.martinlacorrona.ryve.api.rest.model.directions.DirectionsResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface DirectionsAPI {
    String BASE_URL = "https://maps.googleapis.com/maps/api/";
    String API_KEY = "USE_YOUR_DIRECTIONS_API_KEY";

    /**
     * Petición get al servicio de Directions
     *
     * @return Objeto ResponseApi con la lista de estaciones de servicio
     */
    @GET("directions/json?key=" + API_KEY)
    Call<DirectionsResponse> requestRouteByOriginDestinationAndAvoidTolls(
            @Query("origin") String origin, @Query("destination") String destination, @Query("avoid") String avoid);
}