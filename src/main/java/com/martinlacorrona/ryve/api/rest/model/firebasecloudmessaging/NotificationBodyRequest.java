package com.martinlacorrona.ryve.api.rest.model.firebasecloudmessaging;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data@Builder
public class NotificationBodyRequest {
    @JsonProperty("data")
    public NotificationBodyRequestData data;
    @JsonProperty("registration_ids")
    public List<String> registrationIds;
}
