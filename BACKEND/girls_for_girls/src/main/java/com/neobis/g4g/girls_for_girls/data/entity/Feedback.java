package com.neobis.g4g.girls_for_girls.data.entity;

import com.neobis.g4g.girls_for_girls.data.dto.FeedbackDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "feedback")
public class Feedback {

    public Feedback(Timestamp recTime, String fullName, String email, String phoneNumber, String message, VideoCourse videoCourse) {
        this.recTime = recTime;
        this.fullName = fullName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.message = message;
        this.videoCourse = videoCourse;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "rec_time")
    private Timestamp recTime;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "email")
    private String email;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "message")
    private String message;

    @ManyToOne
    @JoinColumn(name = "video_course_id")
    private VideoCourse videoCourse;

}
