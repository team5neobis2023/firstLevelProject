package com.neobis.g4g.girls_for_girls.data.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Collections;
import java.util.Set;

@Entity
@Getter
@Setter
@ToString
@Table(name = "users")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @CreationTimestamp
    @Column(name = "rec_time")
    private Timestamp dateOfRegister;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email")
    private String email;

    @JsonIgnore
    @Column(name = "password")
    private String password;

    @Column(name = "place_of_birth")
    private String placeOfBirth;

    @Column(name = "phone_number")
    private String phoneNumber;

    @ManyToOne
    @JoinColumn(name = "group_id")
    private UserGroup role;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "file_id")
    private File file;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private Set<Order> orderEntities;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userId")
    private Set<Article> articleEntities;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userId")
    private Set<Training> trainingEntities;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userId")
    private Set<Conferences> conferencesEntities;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userId")
    private Set<MentorProgram> mentorProgramEntities;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userId")
    private Set<Notification> notificationEntities;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userId")
    private Set<VideoCourse> videoCourseEntities;

    @Column(name = "reset_token")
    private String resetToken;

    @Column(name = "enabled")
    private boolean enabled;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority(role.getName()));
    }

    @Override
    public String getUsername() {return email;}

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public boolean isAccountNonExpired() {return true;}

    @Override
    public boolean isAccountNonLocked() {return true;}

    @Override
    public boolean isCredentialsNonExpired() {return true;}

    @Override
    public boolean isEnabled() {return enabled;}
}