
package com.martinlacorrona.ryve.api.rest.model.directions;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class OverviewPolyline {
    @JsonProperty("points")
    private String points;
}
