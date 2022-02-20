package com.martinlacorrona.ryve.api.rest.model.directions;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jdk.nashorn.internal.objects.annotations.Constructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Route {
    @JsonProperty("overview_polyline")
    private OverviewPolyline overviewPolyline;
    @JsonProperty("bounds")
    private Bounds bounds;
    @JsonProperty("legs")
    private List<Leg> legs = null;
}
