package com.neobis.g4g.girls_for_girls.data.entity;

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
public class FileEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "name")
    private String name;
    @Column(name = "file_code")
    private String fileCode;
    @Column(name = "is_deleted")
    private Boolean isDeleted;
    @CreationTimestamp
    @Column(name = "rec_time")
    private Timestamp recTime;

    @OneToMany(mappedBy = "file")
    private Set<UserEntity> users;

    @OneToMany(mappedBy = "productGroupId")
    private Set<ProductEntity> products;

    @ManyToOne
    @JoinColumn(name = "article")
    private ArticleEntity article;
}

