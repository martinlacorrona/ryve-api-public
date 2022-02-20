package com.martinlacorrona.ryve.api.controller;

import com.martinlacorrona.ryve.api.model.FavouriteStationServiceModel;
import com.martinlacorrona.ryve.api.service.UserFavouriteStationService;
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
@RequestMapping(value = "/v1/userfavouritestation")
public class UserFavouriteStationController extends RyveController {

    private static final Logger logger = LoggerFactory.getLogger(UserFavouriteStationController.class);

    @Autowired
    private UserFavouriteStationService userFavouriteStationService;

    @RequestMapping(value = "", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<FavouriteStationServiceModel>> getFavouriteStations(){
        logger.info("UserFavouriteStationController::getFavouriteStations()");

        return new ResponseEntity<>(userFavouriteStationService.getFavourites(), HttpStatus.OK);
    }

    @RequestMapping(value = "", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<FavouriteStationServiceModel>> addFavouriteStationByStationId(Long idStation){
        logger.info("UserFavouriteStationController::addFavouriteStationByStationId({})", idStation);

        return new ResponseEntity<>(userFavouriteStationService.addFavourite(idStation), HttpStatus.OK);
    }

    @RequestMapping(value = "", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<FavouriteStationServiceModel>> deleteFavouriteStationByStationId(Long idStation){
        logger.info("UserFavouriteStationController::deleteFavouriteStationByStationId({})", idStation);

        return new ResponseEntity<>(userFavouriteStationService.removeFavourite(idStation), HttpStatus.OK);
    }
}
