package com.neobis.g4g.girls_for_girls.data.dto;

import lombok.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RatingDTO {
    @NotNull(message = "Айди не может быть null")
    private Long videoCourseId;

    @NotNull(message = "Рейтинг не может быть null")
    @Min(value = 1, message = "Рейтинг минимум должен быть равен 1")
    @Max(value = 5, message = "Рейтинг максимум может быть равен 5")
    private int rating;
}
