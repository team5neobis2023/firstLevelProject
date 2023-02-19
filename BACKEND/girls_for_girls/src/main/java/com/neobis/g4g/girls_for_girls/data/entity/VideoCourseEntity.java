package com.neobis.g4g.girls_for_girls.data.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Set;

@Data
@Entity
@NoArgsConstructor
@Table(name = "video_course")
public class VideoCourseEntity {

    public VideoCourseEntity(Timestamp recTime, String description, int raiting, UserEntity userId) {
        this.recTime = recTime;
        this.description = description;
        this.raiting = raiting;
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

    @Column(name = "raiting")
    private int raiting;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity userId;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "videoCourse")
    private Set<FeedbackEntity> feedbackEntitySet;
}
