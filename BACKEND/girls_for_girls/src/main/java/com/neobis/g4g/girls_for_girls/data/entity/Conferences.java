package com.neobis.g4g.girls_for_girls.data.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Set;

@Data
@Entity
@NoArgsConstructor
@Table(name = "conferences")
public class Conferences {

    public Conferences(Timestamp recTime, Timestamp conferenceDate, String description, User userId) {
        this.recTime = recTime;
        this.conferenceDate = conferenceDate;
        this.description = description;
        this.userId = userId;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "rec_time")
    private Timestamp recTime;

    @Column(name = "conference_date")
    private Timestamp conferenceDate;

    @Column(name = "description")
    private String description;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User userId;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "conferencesId")
    private Set<Application> applicationEntities;

}
