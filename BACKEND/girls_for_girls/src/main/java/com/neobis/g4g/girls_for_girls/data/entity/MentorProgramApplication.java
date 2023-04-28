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
@Table(name = "mentor_program_application")
public class MentorProgramApplication {
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

    @Column(name = "goals")
    private String goals;

    @Column(name = "help_of_program")
    private String helpOfProgram;

    @Column(name = "experience_with_mentor")
    private String experienceWithMentor;

    @Column(name = "ideal_mentor_desc")
    private String idealMentorDesc;

    @Column(name = "resume_url")
    private String resumeUrl;

    @Column(name = "where_found")
    private String whereFound;

    @Column(name = "approved")
    private boolean approved;

    @ManyToOne
    @JoinColumn(name = "mentor_program_id")
    private MentorProgram mentorProgram;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
