package com.martinlacorrona.ryve.api.rest.model.directions;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Duration {
    @JsonProperty("text")
    private String text;
    @JsonProperty("value")
    private Integer value;
}
