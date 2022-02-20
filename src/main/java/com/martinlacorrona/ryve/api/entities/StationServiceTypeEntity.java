package com.martinlacorrona.ryve.api.entities;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name= "station_service_type")
@NoArgsConstructor @AllArgsConstructor @Builder @ToString
public class StationServiceTypeEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", unique = true, nullable = false)
    @Getter
    @Setter@EqualsAndHashCode.Include
    private Long id;

    @Column(name = "name")
    @Getter@Setter
    private String name;
}
