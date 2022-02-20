package com.martinlacorrona.ryve.api.rest.model.firebasecloudmessaging;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.math.BigInteger;

@Data
public class FirebaseCloudMessagingResponse {

    @JsonProperty("multicast_id")
    private BigInteger multicastId;
    @JsonProperty("success")
    private Integer success;
    @JsonProperty("failure")
    private Integer failure;
    @JsonProperty("canonical_ids")
    private Integer canonicalIds;
    @JsonIgnore //No nos hace falta el ID
    @JsonProperty("results")
    private String results = null;
}
