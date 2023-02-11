package com.neobis.g4g.girls_for_girls.data.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@Data
@Entity
@Table(name = "product")
public class ProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "price")
    private int price;

    @Column(name = "size")
    private String size;

    @ManyToOne
    @JoinColumn(name = "file_id")
    private FileEntity fileId;

    @ManyToOne
    @JoinColumn(name = "product_group_id")
    private ProductGroupEntity productGroupId;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "product")
    private Set<OrderEntity> orderEntities;
}
