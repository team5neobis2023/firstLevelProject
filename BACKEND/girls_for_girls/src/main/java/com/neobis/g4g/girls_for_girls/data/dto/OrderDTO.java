package com.neobis.g4g.girls_for_girls.data.dto;

import com.neobis.g4g.girls_for_girls.data.entity.Order;
import com.neobis.g4g.girls_for_girls.data.entity.Product;
import com.neobis.g4g.girls_for_girls.data.entity.Size;
import lombok.*;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderDTO {
    private Long userId;
    private Product product;
    private Size size;
    private int amount;
    private Timestamp orderDate;

    public static OrderDTO orderToOrderDto(Order order) {
        return OrderDTO.builder()
                .userId(order.getUser().getId())
                .product(order.getProduct())
                .amount(order.getAmount())
                .orderDate(order.getOrderDate())
                .size(order.getSize())
                .build();
    }

    public static List<OrderDTO> orderToOrderDtoList(List<Order> orders) {
        return orders.stream().map(OrderDTO::orderToOrderDto).collect(Collectors.toList());
    }
}
