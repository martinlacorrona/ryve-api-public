package com.martinlacorrona.ryve.api.controller;

import com.martinlacorrona.ryve.api.model.HistoryStationServiceModel;
import com.martinlacorrona.ryve.api.model.StationServiceModel;
import com.martinlacorrona.ryve.api.model.reduced.HistoryStationServiceModelReduced;
import com.martinlacorrona.ryve.api.service.StationServiceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/v1/stationservice")
public class StationServiceController extends RyveController {

    private static final Logger logger = LoggerFactory.getLogger(StationServiceController.class);

    @Autowired
    private StationServiceService stationServiceService;

    @RequestMapping(value = "/list", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<StationServiceModel>>
    getAllStationService() {
        logger.info("StationServiceController::getAllStationService()");

        return new ResponseEntity<>(
                stationServiceService.getAllStationService(), HttpStatus.OK);
    }

    @RequestMapping(value = "", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<HistoryStationServiceModelReduced>>
    getAllHistoryStationServiceFromTodayByFuelId(@RequestParam Long fuelTypeId) {
        logger.info("StationServiceController::getAllHistoryStationServiceFromTodayByFuelId({})", fuelTypeId);

        return new ResponseEntity<>(
                stationServiceService.getAllHistoryStationServiceLastestByFuelType(fuelTypeId), HttpStatus.OK);
    }

    @RequestMapping(value = "/ccaa", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<HistoryStationServiceModelReduced>>
    getAllHistoryStationServiceFromTodayByFuelIdAndIdCCAA(@RequestParam Long fuelTypeId, @RequestParam Integer idCCAA) {
        logger.info("StationServiceController::getAllHistoryStationServiceFromTodayByFuelIdAndIdCCAA({}, {})",
                fuelTypeId, idCCAA);

        return new ResponseEntity<>(
                stationServiceService.getAllHistoryStationServiceFromLastestByFuelTypeAndCCAA(fuelTypeId, idCCAA),
                HttpStatus.OK);
    }

    @RequestMapping(value = "/id", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<HistoryStationServiceModel> getHistoryStationServiceFromTodayByIdAndFuelTypeId(@RequestParam Long id, @RequestParam Long fuelTypeId) {
        logger.info("StationServiceController::getHistoryStationServiceFromTodayById({}, {})", id, fuelTypeId);

        return new ResponseEntity<>(stationServiceService.getHistoryStationServiceFromLastestById(id, fuelTypeId), HttpStatus.OK);
    }

    @RequestMapping(value = "/history", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<HistoryStationServiceModelReduced>> getHistoryStationServiceHistoryByIdAndFuelTypeId(
            @RequestParam Long stationServiceId, @RequestParam Long fuelTypeId) {
        logger.info("StationServiceController::getHistoryStationServiceFromTodayById({}, {})", stationServiceId, fuelTypeId);

        return new ResponseEntity<>(
                stationServiceService.getHistoryStationServiceHistoryByIdAndFuelTypeIdLast30Days(stationServiceId,
                        fuelTypeId), HttpStatus.OK);
    }
}
