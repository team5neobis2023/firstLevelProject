package com.neobis.g4g.girls_for_girls.data.entity;


import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "training_application")
public class TrainingApplication {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "rec_time")
    @CreationTimestamp
    private Timestamp recTime;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "date_of_birth")
    private Timestamp dateOfBirth;

    @Column(name = "email")
    private String email;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "address")
    private String address;

    @Column(name = "opportunity_to_come")
    private String opportunityToCome;

    @Column(name = "reason_to_participate")
    private String reasonToParticipate;

    @Column(name = "expectation")
    private String expectation;

    @Column(name = "most_interest_training_topic")
    private String mostInterestTrainingTopic;

    @Column(name = "other_interest_topics")
    private String otherInterestTopics;

    @Column(name = "where_found_training")
    private String whereFoundTraining;

    @Column(name = "approved")
    private boolean approved;

    @ManyToOne
    @JoinColumn(name = "training_id")
    private Training training;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

}
