package com.neobis.g4g.girls_for_girls.data.dto;

import com.neobis.g4g.girls_for_girls.data.entity.FileEntity;
import com.neobis.g4g.girls_for_girls.data.entity.UserGroupEntity;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class UserDTO {
    private int id;
    private String email;
    private String password;
    private UserGroupEntity role;
    private Timestamp dateOfBirth;
    private FileEntity file;
    private String phoneNumber;
}
