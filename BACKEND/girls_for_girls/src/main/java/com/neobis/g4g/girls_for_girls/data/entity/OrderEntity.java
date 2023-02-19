package com.neobis.g4g.girls_for_girls.data.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@Entity
@NoArgsConstructor
@Table(name = "order")
public class OrderEntity {

    public OrderEntity(UserEntity user, ProductEntity product, int amount, Timestamp orderDate) {
        this.user = user;
        this.product = product;
        this.amount = amount;
        this.orderDate = orderDate;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private ProductEntity product;

    @Column(name = "amount")
    private int amount;

    @Column(name = "order_date")
    private Timestamp orderDate;

}
