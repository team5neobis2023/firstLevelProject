package com.neobis.g4g.girls_for_girls.data.dto;

import com.neobis.g4g.girls_for_girls.data.entity.Order;
import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
public class OrderDTO {

    private Long userId;
    private Long productId;
    private int amount;
    private Timestamp orderDate;

    public static OrderDTO orderToOrderDto(Order order) {
        return OrderDTO.builder()
                .userId(order.getUser().getId())
                .productId(order.getProduct().getId())
                .amount(order.getAmount())
                .orderDate(order.getOrderDate())
                .build();
    }

    public static List<OrderDTO> orderToOrderDtoList(List<Order> orders) {
        return orders.stream().map(OrderDTO::orderToOrderDto).collect(Collectors.toList());
    }
}
