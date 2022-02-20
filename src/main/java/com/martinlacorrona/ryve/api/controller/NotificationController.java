package com.martinlacorrona.ryve.api.controller;

import com.martinlacorrona.ryve.api.model.SubscribeNotificationModel;
import com.martinlacorrona.ryve.api.service.FirebaseCloudMessagingService;
import com.martinlacorrona.ryve.api.service.SubscribeNotificationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/v1/notification")
public class NotificationController extends RyveController {

    private static final Logger logger = LoggerFactory.getLogger(NotificationController.class);

    @Autowired
    private FirebaseCloudMessagingService firebaseCloudMessagingService;

    @Autowired
    private SubscribeNotificationService suscribeNotificationService;

    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<Void> registertoken(@RequestParam String tokenFirebase){
        logger.info("NotificationController::registertoken({})", tokenFirebase);
        firebaseCloudMessagingService.saveToken(tokenFirebase);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "", method = RequestMethod.DELETE)
    public ResponseEntity<Void> unregisterToken(@RequestParam String tokenFirebase){
        logger.info("NotificationController::unregisterToken({})", tokenFirebase);
        firebaseCloudMessagingService.deleteToken(tokenFirebase);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/subscribe", method = RequestMethod.GET)
    public ResponseEntity<List<SubscribeNotificationModel>> getSubscribeNotification(){
        logger.info("NotificationController::getSubscribeNotification()");
        return new ResponseEntity<>(suscribeNotificationService.get(), HttpStatus.OK);
    }

    @RequestMapping(value = "/subscribe", method = RequestMethod.POST)
    public ResponseEntity<List<SubscribeNotificationModel>> subscribeNotification(@RequestParam Long stationServiceId,
                                                                                 @RequestParam Long fuelTypeId,
                                                                                 @RequestParam Integer periodInDays){
        logger.info("NotificationController::subscribeNotification({}, {}, {})", stationServiceId, fuelTypeId, periodInDays);
        return new ResponseEntity<>(
                suscribeNotificationService
                        .suscribeStationServiceByIdAndFuelIdAndPeriod(stationServiceId, fuelTypeId, periodInDays),
                HttpStatus.OK);
    }

    @RequestMapping(value = "/subscribe", method = RequestMethod.DELETE)
    public ResponseEntity<List<SubscribeNotificationModel>> unsubscribeNotification(@RequestParam Long subscribeNotificationId){
        logger.info("NotificationController::unsubscribeNotification({})", subscribeNotificationId);
        return new ResponseEntity<>(
                suscribeNotificationService.unsuscribeBySubscribeNotificationId(subscribeNotificationId),
                HttpStatus.OK);
    }
}
