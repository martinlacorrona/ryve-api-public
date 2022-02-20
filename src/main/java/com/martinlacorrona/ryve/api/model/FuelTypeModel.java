package com.martinlacorrona.ryve.api.model;

import lombok.*;
import java.io.Serializable;

@Data
public class FuelTypeModel implements Serializable {
    private Long id;
    private String name;
    private String unit;
}
