package com.neobis.g4g.girls_for_girls.data.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "article")
public class Article {

    public Article(Timestamp recTime, Timestamp updateTime, String title, String description, Long viewsCount, List<User> likes, User userId) {
        this.recTime = recTime;
        this.updateTime = updateTime;
        this.title = title;
        this.description = description;
        this.viewsCount = viewsCount;
        this.likes = likes;
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

    @ManyToMany(mappedBy = "likedArticles")
    private List<User> likes;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User userId;
}
