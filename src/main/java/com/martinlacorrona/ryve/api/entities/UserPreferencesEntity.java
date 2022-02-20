package com.martinlacorrona.ryve.api.entities;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity
@Table(name="user_preferences")
@NoArgsConstructor @AllArgsConstructor @Builder @ToString
public class UserPreferencesEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    @Getter @Setter
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_user")
    @Getter@Setter
    private UserEntity user;

    @Getter@Setter
    private String carname;

    @Getter@Setter
    private String carcolor;

    @Getter@Setter
    private Double kmRange;

    @OneToOne
    @JoinColumn(name = "id_favourite_fuel_type")
    @Getter@Setter
    private FuelTypeEntity favouriteFuel;

    @Getter@Setter
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "user_favourite_station_service",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "station_service_id"))
    private Set<StationServiceEntity> favouriteStationService;
}
