package com.neobis.g4g.girls_for_girls.data.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TokenDTO {
    private Integer userId;
    private String accessToken;
    private String refreshToken;
}
