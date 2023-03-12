package com.neobis.g4g.girls_for_girls.data.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Set;

@Getter
@Setter
@ToString
@Entity
@Table(name = "file")
public class File {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "file_code")
    private String fileCode;
    @Column(name = "is_deleted")
    private Boolean isDeleted;
    @CreationTimestamp
    @Column(name = "rec_time")
    private Timestamp recTime;

    @Column(name = "product_id")
    private int productId;

    @Column(name = "article_id")
    private int articleId;

    @Column(name = "user_id")
    private int userId;

    @JsonIgnore
    @OneToMany(mappedBy = "file")
    private Set<User> users;
}

