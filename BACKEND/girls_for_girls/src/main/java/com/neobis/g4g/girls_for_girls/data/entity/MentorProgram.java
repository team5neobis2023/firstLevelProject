package com.neobis.g4g.girls_for_girls.data.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "mentor_program")
public class MentorProgram {

    public MentorProgram(String description, Timestamp recTime, User userId) {
        this.description = description;
        this.recTime = recTime;
        this.userId = userId;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "description")
    private String description;

    @Column(name = "rec_time")
    private Timestamp recTime;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User userId;

}
