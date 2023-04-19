package com.neobis.g4g.girls_for_girls.data.dto;

import com.neobis.g4g.girls_for_girls.data.entity.Order;
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
    private Long productId;
    private Long sizeId;
    private int amount;
    private Timestamp orderDate;

    public static OrderDTO orderToOrderDto(Order order) {
        return OrderDTO.builder()
                .userId(order.getUser().getId())
                .productId(order.getProduct().getId())
                .amount(order.getAmount())
                .orderDate(order.getOrderDate())
                .sizeId(order.getSize().getId())
                .build();
    }

    public static List<OrderDTO> orderToOrderDtoList(List<Order> orders) {
        return orders.stream().map(OrderDTO::orderToOrderDto).collect(Collectors.toList());
    }
}
