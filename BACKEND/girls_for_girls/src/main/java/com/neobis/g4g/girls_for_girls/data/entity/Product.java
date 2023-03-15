package com.neobis.g4g.girls_for_girls.data.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@Data
@Entity
@Table(name = "product")
public class Product {

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
    private File file;

    @ManyToOne
    @JoinColumn(name = "product_group_id")
    private ProductGroup productGroup;
}
