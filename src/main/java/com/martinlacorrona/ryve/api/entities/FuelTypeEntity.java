package com.martinlacorrona.ryve.api.entities;

import lombok.*;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="fuel_type")
@NoArgsConstructor @AllArgsConstructor @Builder @ToString
public class FuelTypeEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    @Getter@Setter@EqualsAndHashCode.Include
    private Long id;

    @Column(name = "name")
    @Getter@Setter
    private String name;

    @Column(name = "unit")
    @Getter@Setter
    private String unit;

    @Getter@Setter
    @Type(type="numeric_boolean")
    private boolean enabled;
}
