package com.neobis.g4g.girls_for_girls.data.entity;

import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "video_course")
public class VideoCourse {

    public VideoCourse(Timestamp recTime, String description, int rating, User userId) {
        this.recTime = recTime;
        this.description = description;
        this.rating = rating;
        this.userId = userId;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "rec_time")
    private Timestamp recTime;

    @Column(name = "description")
    private String description;

    @Column(name = "rating")
    private int rating;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User userId;

}
