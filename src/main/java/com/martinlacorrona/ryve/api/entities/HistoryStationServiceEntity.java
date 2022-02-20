package com.martinlacorrona.ryve.api.entities;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name="history_station_service", uniqueConstraints={
        @UniqueConstraint(columnNames = {"station_service_id", "fuel_type_id", "date"})})
@NoArgsConstructor @AllArgsConstructor @Builder @ToString
public class HistoryStationServiceEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    @Getter@Setter
    private Long id;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "station_service_id")
    @Getter@Setter
    private StationServiceEntity stationService;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "fuel_type_id")
    @Getter@Setter
    private FuelTypeEntity fuelType;

    @Getter@Setter
    @Temporal(TemporalType.DATE)
    private Date date;

    @Getter@Setter
    @Temporal(TemporalType.TIMESTAMP)
    private Date datetime;

    @Getter@Setter
    private Double price;
}
