package com.martinlacorrona.ryve.api.controller;

import com.martinlacorrona.ryve.api.model.DirectionsModel;
import com.martinlacorrona.ryve.api.rest.model.directions.DirectionsResponse;
import com.martinlacorrona.ryve.api.service.DirectionsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/v1/directions")
public class DirectionsController extends RyveController {

    private static final Logger logger = LoggerFactory.getLogger(DirectionsController.class);

    @Autowired
    private DirectionsService directionsService;

    @RequestMapping(value = "", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DirectionsModel> requestRouteByOriginDestinationAndAvoidTolls(String origin, String destination, Boolean avoid) {
        logger.info("DirectionsController::requestRouteByOriginDestinationAndAvoidTolls({}, {}, {})", origin, destination, avoid);

        return new ResponseEntity<>(directionsService.requestRouteByOriginDestinationAndAvoidTolls(origin, destination, avoid), HttpStatus.OK);
    }
}
