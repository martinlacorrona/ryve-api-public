package com.martinlacorrona.ryve.api.entities;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="user_favourite_station_service")
@NoArgsConstructor @AllArgsConstructor @Builder @ToString
public class UserFavouriteStationServiceEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    @Getter @Setter
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @Getter @Setter
    private UserEntity user;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "station_service_id")
    @Getter @Setter
    private StationServiceEntity stationService;

}
