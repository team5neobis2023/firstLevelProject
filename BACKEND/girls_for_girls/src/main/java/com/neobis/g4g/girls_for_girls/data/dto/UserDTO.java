package com.neobis.g4g.girls_for_girls.data.dto;

import com.neobis.g4g.girls_for_girls.data.entity.File;
import com.neobis.g4g.girls_for_girls.data.entity.User;
import com.neobis.g4g.girls_for_girls.data.entity.UserGroup;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDTO {
    @Email(message = "Введите электронную почту корректно")
    private String email;

    @Size(min = 2, max = 30, message = "Имя должно содержать от 2 до 30 символов")
    private String firstName;

    @Size(min = 2, max = 30, message = "Фамилия должна содержать от 2 до 30 символов")
    private String lastName;

    @Size(min = 8, message = "Пароль должен содержать от 8 символов")
    private String password;

    private String confirmPass;

    @NotEmpty(message = "Место рождения не может быть пустым")
    private String placeOfBirth;

    private Long file_id;

    @NotEmpty(message = "Номер телефона не может быть пустым")
    private String phoneNumber;

    public static UserDTO toUserDTO(User user){
        return UserDTO.builder()
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .password(user.getPassword())
                .placeOfBirth(user.getPlaceOfBirth())
                .file_id(user.getFile().getId())
                .phoneNumber(user.getPhoneNumber())
                .build();
    }

    public static List<UserDTO> toUserDTO(List<User> users){
        return users.stream().map(UserDTO::toUserDTO).collect(Collectors.toList());
    }
}
