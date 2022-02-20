package com.martinlacorrona.ryve.api.entities;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name= "last_update")
@NoArgsConstructor @AllArgsConstructor @Builder @ToString
public class LastUpdateEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    @Getter
    @Setter@EqualsAndHashCode.Include
    private Long id;

    @Getter@Setter
    @Temporal(TemporalType.TIMESTAMP)
    private Date datetime;
}
