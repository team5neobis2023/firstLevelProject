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
public class MentorProgramEntity {

    public MentorProgramEntity(String description, Timestamp recTime, UserEntity userId) {
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
    private UserEntity userId;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "mentorProgramId")
    private Set<ApplicationEntity> applicationEntities;
}
