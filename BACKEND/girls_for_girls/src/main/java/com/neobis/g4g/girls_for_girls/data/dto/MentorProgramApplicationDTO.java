package com.neobis.g4g.girls_for_girls.data.dto;

import com.neobis.g4g.girls_for_girls.data.entity.MentorProgram;
import com.neobis.g4g.girls_for_girls.data.entity.MentorProgramApplication;
import com.neobis.g4g.girls_for_girls.data.entity.TrainingApplication;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MentorProgramApplicationDTO {
    @NotBlank(message = "ФИО не может быть пустым")
    private String fullName;

    private Timestamp dateOfBirth;

    @NotBlank(message = "Электронная почта не может быть пустой")
    @Email(message = "Введите почту корректно")
    private String email;

    @NotBlank(message = "Номер телефона не может быть пустым")
    private String phoneNumber;

    @NotBlank(message = "Адрес не может быть пустым")
    private String address;

    private String goals;

    private String helpOfProgram;

    private String experienceWithMentor;

    private String idealMentorDesc;

    private String whereFound;

    private long mentorProgram_id;

    public static MentorProgramApplicationDTO toMentorProgramApplicationDTO(MentorProgramApplication mentorProgramApplication) {
        return MentorProgramApplicationDTO.builder()
                .fullName(mentorProgramApplication.getFullName())
                .dateOfBirth(mentorProgramApplication.getDateOfBirth())
                .email(mentorProgramApplication.getEmail())
                .phoneNumber(mentorProgramApplication.getPhoneNumber())
                .address(mentorProgramApplication.getAddress())
                .goals(mentorProgramApplication.getGoals())
                .helpOfProgram(mentorProgramApplication.getHelpOfProgram())
                .experienceWithMentor(mentorProgramApplication.getExperienceWithMentor())
                .idealMentorDesc(mentorProgramApplication.getIdealMentorDesc())
                .whereFound(mentorProgramApplication.getWhereFound())
                .mentorProgram_id(mentorProgramApplication.getMentorProgram().getId())
                .build();
    }

    public static List<MentorProgramApplicationDTO> toMentorProgramApplicationDTO(List<MentorProgramApplication> mentorProgramApplications){
        return mentorProgramApplications.stream().map(MentorProgramApplicationDTO::toMentorProgramApplicationDTO).collect(Collectors.toList());
    }
}
