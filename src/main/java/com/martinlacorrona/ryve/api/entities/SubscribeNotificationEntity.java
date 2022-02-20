package com.martinlacorrona.ryve.api.entities;

import lombok.*;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name="subscribe_notification")
@NoArgsConstructor @AllArgsConstructor @Builder @ToString
public class SubscribeNotificationEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    @Getter@Setter@EqualsAndHashCode.Include
    private Long id;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "station_service_id")
    @Getter@Setter
    private StationServiceEntity stationService;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @Getter@Setter
    private UserEntity user;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "fuel_type_id")
    @Getter@Setter
    private FuelTypeEntity fuelType;

    @Getter@Setter
    @Temporal(TemporalType.DATE)
    @Column(name = "last_notified")
    private Date lastNotified;

    @Getter@Setter
    @Temporal(TemporalType.DATE)
    private Date nextNotify;

    @Getter@Setter
    private Integer periodInDays;

    @Getter@Setter
    @Type(type="numeric_boolean")
    private boolean status;
}
