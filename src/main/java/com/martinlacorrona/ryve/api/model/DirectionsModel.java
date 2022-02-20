package com.martinlacorrona.ryve.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.martinlacorrona.ryve.api.rest.model.directions.LatLng;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class DirectionsModel implements Serializable {
    @JsonProperty("pointsEncoded")
    private String pointsEncoded;

    @JsonProperty("northeast")
    private LatLng northeast;
    @JsonProperty("southwest")
    private LatLng southwest;
    @JsonProperty("duration")
    private Integer duration; //in minutes
    @JsonProperty("distance")
    private Integer distance; //in meters
}
