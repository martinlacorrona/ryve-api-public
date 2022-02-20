package com.martinlacorrona.ryve.api.model;

import com.martinlacorrona.ryve.api.model.reduced.StationServiceModelReduced;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class SubscribeNotificationModel implements Serializable {
    private Long id;
    private StationServiceModelReduced stationService;
    private FuelTypeModel fuelType;
    private Date lastNotified;
    private Date nextNotify;
    private Integer periodInDays;
    private boolean status;
}
