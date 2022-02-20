package com.martinlacorrona.ryve.api.entities;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="station_service")
@NoArgsConstructor @AllArgsConstructor @Builder
@Data public class StationServiceEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
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
    @Column(name = "id_ccaa")
    private Integer idCCAA;

    @Column(name = "id_station_api")
    private Long idStationApi;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_station_service_type")
    private StationServiceTypeEntity stationServiceType;
}
