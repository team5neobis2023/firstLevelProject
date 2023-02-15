package com.neobis.g4g.girls_for_girls.data.entity;

import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Set;

@Data
@Entity
@Table(name = "article")
public class ArticleEntity {

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

    @ManyToOne
    @JoinColumn(name = "like_id")
    private UserEntity likeCount;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity userId;
}
