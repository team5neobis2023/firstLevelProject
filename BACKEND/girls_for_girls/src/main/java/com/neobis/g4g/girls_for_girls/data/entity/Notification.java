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
@Table(name = "notification")
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @CreationTimestamp
    @Column(name = "rec_time")
    private Timestamp recTime;

    @Column(name = "message")
    private String message;

    @Column(name = "readed")
    private boolean readed;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

}
