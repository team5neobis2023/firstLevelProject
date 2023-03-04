package com.neobis.g4g.girls_for_girls.data.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@Entity
@NoArgsConstructor
@Table(name = "notification")
public class Notification {

    public Notification(Timestamp recTime, String message, User userId) {
        this.recTime = recTime;
        this.message = message;
        this.userId = userId;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "rec_time")
    private Timestamp recTime;

    @Column(name = "message")
    private String message;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User userId;

}
