package com.neobis.g4g.girls_for_girls.data.dto;

import lombok.*;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SizeDTO {
    @NotEmpty(message = "Название размера не может быть пустым")
    private String name;
}
