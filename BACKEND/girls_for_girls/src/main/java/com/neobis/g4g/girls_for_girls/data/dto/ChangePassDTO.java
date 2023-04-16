package com.neobis.g4g.girls_for_girls.data.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Getter
@Setter
public class ChangePassDTO {

    @NotEmpty(message = "Текущий пароль не может быть пустым")
    private String oldPassword;

    @Size(min = 4, message = "Пароль должен содержать от 4 символов")
    private String newPassword;

    @Size(min = 4, message = "Пароль должен содержать от 4 символов")
    private String confirmNewPassword;
}
