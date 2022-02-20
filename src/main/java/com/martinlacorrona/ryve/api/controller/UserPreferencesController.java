package com.martinlacorrona.ryve.api.controller;

import com.martinlacorrona.ryve.api.model.UserPreferencesModel;
import com.martinlacorrona.ryve.api.service.UserPreferencesService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/v1/userpreferences")
public class UserPreferencesController extends RyveController {

    private static final Logger logger = LoggerFactory.getLogger(UserPreferencesController.class);

    @Autowired
    private UserPreferencesService userPreferencesService;

    @RequestMapping(value = "", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserPreferencesModel> getUserPreferences(){
        logger.info("UserPreferencesController::getUserPreferences()");

        return new ResponseEntity<>(userPreferencesService.get(), HttpStatus.OK);
    }

    @RequestMapping(value = "", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserPreferencesModel> updateUserPreferences(@RequestBody UserPreferencesModel userPreferencesModel){
        logger.info("UserPreferencesController::updateUserPreferences({})", userPreferencesModel);

        validateRequiredParam(userPreferencesModel.getCarname(), "nombre del coche");
        validateRequiredStringParamLenght(userPreferencesModel.getCarname(), "nombre del coche", 4, 24);

        validateRequiredParam(userPreferencesModel.getCarcolor(), "color del coche");
        validateRequiredStringParamLenght(userPreferencesModel.getCarcolor(), "color del coche", 4, 24);

        validateRequiredParam(userPreferencesModel.getKmRange(), "rango del coche");
        validateRequiredDoubleParamLenght(userPreferencesModel.getKmRange(), "rango del coche", 20, 2000);

        return new ResponseEntity<>(userPreferencesService.update(userPreferencesModel), HttpStatus.OK);
    }
}
