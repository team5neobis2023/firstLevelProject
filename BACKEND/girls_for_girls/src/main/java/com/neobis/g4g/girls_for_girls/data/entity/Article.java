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
@Table(name = "article")
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "rec_time")
    private Timestamp recTime;

    @Column(name = "update_time")
    private Timestamp updateTime;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "views_count")
    private Long viewsCount;

    @Column(name = "image_url")
    private String image_url;

    @ManyToMany(mappedBy = "likedArticles")
    @JsonIgnore
    private List<User> likedUsers;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
