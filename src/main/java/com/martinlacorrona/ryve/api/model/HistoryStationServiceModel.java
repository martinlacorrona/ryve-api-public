package com.martinlacorrona.ryve.api.model;

import lombok.Data;
import java.io.Serializable;
import java.util.Date;

@Data
public class HistoryStationServiceModel implements Serializable {
    private Long id;
    private StationServiceModel stationService;
    private FuelTypeModel fuelType;
    private Date date;
    private Date datetime;
    private Double price;
}
