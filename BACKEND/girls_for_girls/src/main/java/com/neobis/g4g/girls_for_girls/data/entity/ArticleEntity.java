package com.neobis.g4g.girls_for_girls.data.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@Entity
@NoArgsConstructor
@Table(name = "article")
public class ArticleEntity {

    public ArticleEntity(Timestamp recTime, Timestamp updateTime, String title, String description, Long viewsCount, UserEntity likeCount, UserEntity userId) {
        this.recTime = recTime;
        this.updateTime = updateTime;
        this.title = title;
        this.description = description;
        this.viewsCount = viewsCount;
        this.likeCount = likeCount;
        this.userId = userId;
    }

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
