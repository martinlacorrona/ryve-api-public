package com.martinlacorrona.ryve.api.entities;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name="notification_token")
@NoArgsConstructor @AllArgsConstructor @Builder @ToString
public class NotificationTokenEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    @Getter @Setter
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @Getter @Setter
    private UserEntity user;

    @Getter @Setter
    private String token;

    @Getter @Setter
    @Temporal(TemporalType.DATE)
    private Date lastuse;
}
