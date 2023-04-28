package com.neobis.g4g.girls_for_girls.data.dto;

import com.neobis.g4g.girls_for_girls.data.entity.Basket;
import com.neobis.g4g.girls_for_girls.data.entity.Product;
import com.neobis.g4g.girls_for_girls.data.entity.Size;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetBasketDTO {
    private long id;

    private Product product;

    private Size size;

    private int amount;

    public static GetBasketDTO toGetBasketDTO(Basket basket){
        return GetBasketDTO.builder()
                .id(basket.getId())
                .product(basket.getProduct())
                .size(basket.getSize())
                .amount(basket.getAmount())
                .build();
    }

    public static List<GetBasketDTO> toGetBasketDTO(List<Basket> baskets){
        return baskets.stream().map(GetBasketDTO::toGetBasketDTO).collect(Collectors.toList());
    }

}
