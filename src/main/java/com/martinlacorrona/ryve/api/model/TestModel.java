package com.martinlacorrona.ryve.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.io.Serializable;

@Data
public class TestModel implements Serializable {
    @JsonProperty("id")
    private Long id;

    @JsonProperty("testName")
    private String testName;

}
