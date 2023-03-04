package com.neobis.g4g.girls_for_girls.data.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Set;

@Data
@Entity
@NoArgsConstructor
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

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "mentorProgramId")
    private Set<Application> applicationEntities;
}
