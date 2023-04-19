package com.neobis.g4g.girls_for_girls.data.dto;

import com.neobis.g4g.girls_for_girls.data.entity.User;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetLikedUserDTO {
    private String firstName;
    private String lastName;
    private String image_url;

    public static GetLikedUserDTO toGetLikedUserDTO(User user){
        return GetLikedUserDTO.builder()
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .image_url(user.getImage_url())
                .build();
    }

    public static List<GetLikedUserDTO> toGetLikedUserDTO(List<User> users){
        return users.stream().map(GetLikedUserDTO::toGetLikedUserDTO).collect(Collectors.toList());
    }
}
