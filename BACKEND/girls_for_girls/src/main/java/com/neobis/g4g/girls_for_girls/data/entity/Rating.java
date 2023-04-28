package com.neobis.g4g.girls_for_girls.data.entity;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "rating")
public class Rating {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "video_course_id")
    private VideoCourse videoCourse;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private int rating;
}
