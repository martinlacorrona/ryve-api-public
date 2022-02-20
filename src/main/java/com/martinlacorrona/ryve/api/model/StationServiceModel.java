package com.martinlacorrona.ryve.api.model;

import lombok.*;
import java.io.Serializable;

@Data
public class StationServiceModel implements Serializable {
    private Long id;

    private String name; //rotulo

    private Double longitude;
    private Double latitude;

    private String schedule;

    private String postalCode;
    private String address;

    private String town; //localidad
    private String municipality; //municipado
    private String district; //provincia

    private Integer idMunicipality;
    private Integer idDistrict;
    private Integer idCCAA;
    private Long idStationApi;

    private StationServiceTypeModel stationServiceType;
}
