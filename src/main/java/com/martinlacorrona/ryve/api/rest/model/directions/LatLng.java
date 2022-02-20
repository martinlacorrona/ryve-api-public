package com.martinlacorrona.ryve.api.rest.model.directions;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class LatLng {
    @JsonProperty("lat")
    private Double lat;
    @JsonProperty("lng")
    private Double lng;
}
