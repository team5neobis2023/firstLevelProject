package com.neobis.g4g.girls_for_girls.data.entity;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "refresh_token")
public class RefreshToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @CreationTimestamp
    @Column(name = "rec_time")
    private Timestamp recTime;

    @Column(name = "token")
    private String token;

    @Column(name = "user_id")
    private long userId;
}
