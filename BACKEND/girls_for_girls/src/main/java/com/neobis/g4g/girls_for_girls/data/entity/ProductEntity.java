package com.neobis.g4g.girls_for_girls.data.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Data
@Entity
@NoArgsConstructor
@Table(name = "product")
public class ProductEntity {

    public ProductEntity(String title, String description, int price, String size, FileEntity fileId, ProductGroupEntity productGroupId) {
        this.title = title;
        this.description = description;
        this.price = price;
        this.size = size;
        this.fileId = fileId;
        this.productGroupId = productGroupId;
    }

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
