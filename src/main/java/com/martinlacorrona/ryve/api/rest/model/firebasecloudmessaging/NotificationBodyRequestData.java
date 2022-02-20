package com.martinlacorrona.ryve.api.rest.model.firebasecloudmessaging;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class NotificationBodyRequestData {
    @JsonProperty("title")
    public String title;
    @JsonProperty("body")
    public String body;
    @JsonProperty("stationServiceId")
    public String stationServiceId;
}
