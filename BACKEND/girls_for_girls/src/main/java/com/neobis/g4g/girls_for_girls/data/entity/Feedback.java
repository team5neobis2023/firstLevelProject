package com.neobis.g4g.girls_for_girls.data.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.neobis.g4g.girls_for_girls.data.dto.FeedbackDTO;
import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "feedback")
public class Feedback {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "rec_time")
    private Timestamp recTime;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "message")
    private String message;

    @ManyToOne
    @JoinColumn(name = "video_course_id")
    private VideoCourse videoCourse;

}
