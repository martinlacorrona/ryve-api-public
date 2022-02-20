package com.martinlacorrona.ryve.api.model.reduced;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class StationServiceModelReduced implements Serializable {
    private Long id;
    private String name;
    private Integer idCCAA;
}
