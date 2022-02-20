package com.martinlacorrona.ryve.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class UserPreferencesModel implements Serializable {
    @JsonProperty("carname")
    private String carname;

    @JsonProperty("carcolor")
    private String carcolor;

    @JsonProperty("kmRange")
    private Double kmRange;

    @JsonProperty("favouriteFuel")
    private FuelTypeModel favouriteFuel;
}
