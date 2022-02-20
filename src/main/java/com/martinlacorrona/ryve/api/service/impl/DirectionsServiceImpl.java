package com.martinlacorrona.ryve.api.service.impl;

import com.martinlacorrona.ryve.api.exception.ErrorMessage;
import com.martinlacorrona.ryve.api.exception.RestException;
import com.martinlacorrona.ryve.api.model.DirectionsModel;
import com.martinlacorrona.ryve.api.rest.DirectionsAPI;
import com.martinlacorrona.ryve.api.rest.RetrofitClientDirections;
import com.martinlacorrona.ryve.api.rest.model.directions.DirectionsResponse;
import com.martinlacorrona.ryve.api.rest.model.directions.Route;
import com.martinlacorrona.ryve.api.service.DirectionsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import retrofit2.Call;
import retrofit2.Response;

import java.io.IOException;

@Service
public class DirectionsServiceImpl implements DirectionsService {

    private static final Logger logger = LoggerFactory.getLogger(DirectionsServiceImpl.class);

    private final DirectionsAPI clienteReqResAPI = RetrofitClientDirections.getClient(DirectionsAPI.BASE_URL)
            .create(DirectionsAPI.class);

    @Override
    public DirectionsModel requestRouteByOriginDestinationAndAvoidTolls(String origin, String destination, Boolean avoid) {
        String tolls = avoid ? "tolls" : null;
        Call<DirectionsResponse> call = clienteReqResAPI.requestRouteByOriginDestinationAndAvoidTolls(origin, destination, tolls);
        logger.info("[DirectionsService] Solicitando ruta.");
        try {
            Response<DirectionsResponse> response = call.execute();
            if (response.code() == 200) {
                DirectionsResponse directionsResponse = response.body();
                if (directionsResponse == null) {
                    logger.info("[DirectionsService] La API ha dado cuerpo null.");
                    throw new RestException(HttpStatus.SERVICE_UNAVAILABLE, ErrorMessage.DIRECTIONS_API_FAILED, new String[]{});
                }
                if (directionsResponse.getStatus().equals("REQUEST_DENIED")) {
                    throw new RestException(HttpStatus.NOT_FOUND, ErrorMessage.DIRECTIONS_API_FAILED, new String[]{});
                }
                if (!directionsResponse.getStatus().equals("REQUEST_DENIED") && !directionsResponse.getStatus().equals("OK")) {
                    throw new RestException(HttpStatus.NOT_FOUND, ErrorMessage.NOT_ROUTE_FOUND, new String[]{});
                }
                Route route = directionsResponse.getRoutes().get(0);
                route.getLegs().forEach(leg -> {
                    leg.getSteps().forEach(step -> {
                        //Le devolvemos que no la encuentra si alguna tiene ferry
                        if(step.getManeuver() != null && step.getManeuver().equals("ferry")) {
                            throw new RestException(HttpStatus.NOT_FOUND, ErrorMessage.NOT_RESULT_FOUND, new String[]{});
                        }
                    });
                });


                logger.info("[DirectionsService] Ruta enviada con exito.");
                return DirectionsModel.builder()
                        .pointsEncoded(route.getOverviewPolyline().getPoints())
                        .northeast(route.getBounds().getNortheast())
                        .southwest(route.getBounds().getSouthwest())
                        .distance(route.getLegs().get(0).getDistance().getValue())
                        .duration(route.getLegs().get(0).getDuration().getValue())
                        .build();
            } else {
                logger.info("[DirectionsService] Error directions api con codigo {}.", response.code());
                throw new RestException(HttpStatus.SERVICE_UNAVAILABLE, ErrorMessage.DIRECTIONS_API_FAILED, new String[]{});
            }
        } catch (IOException e) {
            e.printStackTrace();
            logger.info("[DirectionsService] La API no ha dado respuesta. {}", e.getMessage());
            throw new RestException(HttpStatus.SERVICE_UNAVAILABLE, ErrorMessage.DIRECTIONS_API_FAILED, new String[]{});
        }
    }
}
