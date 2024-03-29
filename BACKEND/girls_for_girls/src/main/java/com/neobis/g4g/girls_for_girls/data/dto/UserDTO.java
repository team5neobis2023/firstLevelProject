package com.neobis.g4g.girls_for_girls.data.dto;

import com.neobis.g4g.girls_for_girls.data.entity.UserGroup;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class UserDTO {
    private long id;
    private String email;
    private String firstName;
    private String lastName;
    private String password;
    private String confirmPass;
    private UserGroup role;
    private Long region_id;
    private String image_url;
    private String phoneNumber;
}
