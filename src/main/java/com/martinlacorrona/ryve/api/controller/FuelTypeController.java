package com.martinlacorrona.ryve.api.controller;

import com.martinlacorrona.ryve.api.model.FuelTypeModel;
import com.martinlacorrona.ryve.api.service.FuelTypeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/v1/fueltype")
public class FuelTypeController extends RyveController {

    private static final Logger logger = LoggerFactory.getLogger(FuelTypeController.class);

    @Autowired
    private FuelTypeService fuelTypeService;

    @RequestMapping(value = "", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<FuelTypeModel>> getFuelsType() {
        logger.info("FuelTypeController::getFuelsType()");

        return new ResponseEntity<>(fuelTypeService.getEnabledFuelTypes(), HttpStatus.OK);
    }
}
