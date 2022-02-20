package com.martinlacorrona.ryve.api.rest;

import com.martinlacorrona.ryve.api.rest.model.fuel.GobResponseAPI;
import retrofit2.Call;
import retrofit2.http.GET;

public interface StationServiceFuelAPI {
    String BASE_URL = "https://sedeaplicaciones.minetur.gob.es/ServiciosRESTCarburantes/" +
            "PreciosCarburantes/";

    /**
     * Petición get al servicio
     *
     * @return Objeto ResponseApi con la lista de estaciones de servicio
     */
    @GET("EstacionesTerrestres/")
    Call<GobResponseAPI> getEstaciones();
}
