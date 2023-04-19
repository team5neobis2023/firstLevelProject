package com.neobis.g4g.girls_for_girls.data.dto;

import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddBasketDTO {
    @NotNull(message = "Айди продукта не может быть null")
    private long productId;

    @NotNull(message = "Айди размера не может быть null")
    private long sizeId;

    @NotNull(message = "Количество не может быть null")
    private int amount;
}
