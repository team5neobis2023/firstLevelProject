package com.neobis.g4g.girls_for_girls.data.dto;

import com.neobis.g4g.girls_for_girls.data.entity.File;
import com.neobis.g4g.girls_for_girls.data.entity.User;
import com.neobis.g4g.girls_for_girls.data.entity.UserGroup;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDTO {
    private String email;
    private String firstName;
    private String lastName;
    private String password;
    private String confirmPass;
    private long role_id;
    private String placeOfBirth;
    private long file_id;
    private String phoneNumber;

    public static UserDTO toUserDTO(User user){
        return UserDTO.builder()
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .password(user.getPassword())
                .placeOfBirth(user.getPlaceOfBirth())
                .role_id(user.getRole().getId())
                .file_id(user.getFile().getId())
                .phoneNumber(user.getPhoneNumber())
                .build();
    }
}
