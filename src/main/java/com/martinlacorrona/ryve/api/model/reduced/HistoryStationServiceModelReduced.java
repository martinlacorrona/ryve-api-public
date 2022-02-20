package com.martinlacorrona.ryve.api.model.reduced;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class HistoryStationServiceModelReduced implements Serializable {
    private Long id;
    private StationServiceModelReduced stationService;
    private FuelTypeModelReduced fuelType;
    private Date date;
    private Date datetime;
    private Double price;
}
