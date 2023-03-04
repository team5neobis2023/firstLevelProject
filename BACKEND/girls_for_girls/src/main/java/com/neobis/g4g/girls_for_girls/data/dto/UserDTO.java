package com.neobis.g4g.girls_for_girls.data.dto;

import com.neobis.g4g.girls_for_girls.data.entity.File;
import com.neobis.g4g.girls_for_girls.data.entity.UserGroup;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UserDTO {
    private int id;
    private String email;
    private String firstName;
    private String lastName;
    private String password;
    private String confirmPass;
    private UserGroup role;
    private String placeOfBirth;
    private File file;
    private String phoneNumber;
}
