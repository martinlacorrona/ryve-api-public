package com.martinlacorrona.ryve.api.controller;

import com.martinlacorrona.ryve.api.tasks.SubscribeNotificationSenderTask;
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
@RequestMapping(value = "/v1/test")
public class TestController extends RyveController {

    private static final Logger logger = LoggerFactory.getLogger(TestController.class);

    @Autowired
    private SubscribeNotificationSenderTask subscribeNotificationSenderTask;

    @RequestMapping(value = "", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> test() {
        logger.info("TestController::test()");

        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @RequestMapping(value = "/notification", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> sendNotification() {
        logger.info("TestController::sendingAllNotifications()");

        subscribeNotificationSenderTask.sendAndUpdateAllPendingNotifications();

        return new ResponseEntity<>(null, HttpStatus.OK);
    }
}
