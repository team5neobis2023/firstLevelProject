package com.neobis.g4g.girls_for_girls.data.entity;


import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@Entity
@NoArgsConstructor
@Table(name = "application")
public class ApplicationEntity {

    public ApplicationEntity(Timestamp recTime, String fullName, Timestamp dateOfBirth, String email, String address, String workFormat, String motivation, String aboutMe, String achievements, String myFails, String mySkills) {
        this.recTime = recTime;
        this.fullName = fullName;
        this.dateOfBirth = dateOfBirth;
        this.email = email;
        this.address = address;
        this.workFormat = workFormat;
        this.motivation = motivation;
        this.aboutMe = aboutMe;
        this.achievements = achievements;
        this.myFails = myFails;
        this.mySkills = mySkills;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "rec_time")
    private Timestamp recTime;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "date_of_birth")
    private Timestamp dateOfBirth;

    @Column(name = "email")
    private String email;

    @Column(name = "address")
    private String address;

    @Column(name = "work_format")
    private String workFormat;

    @Column(name = "motivation")
    private String motivation;

    @Column(name = "about_me")
    private String aboutMe;

    @Column(name = "achievements")
    private String achievements;

    @Column(name = "my_fails")
    private String myFails;

    @Column(name = "my_skills")
    private String mySkills;

//    @Column(name = "")
//    private trainingId;
//    TODO Will do connecting other entities
//    @Column(name = "")
//    private mentorProgramId
//
//    @Column(name = "")
//    private conferencesId;

}
