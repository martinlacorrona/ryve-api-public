package com.martinlacorrona.ryve.api.rest.model.directions;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class DirectionsResponse {
    @JsonProperty("routes")
    private List<Route> routes = null;
    @JsonProperty("status")
    private String status;
}
