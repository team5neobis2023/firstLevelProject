package com.neobis.g4g.girls_for_girls.data.dto;

import com.neobis.g4g.girls_for_girls.data.entity.Application;
import com.neobis.g4g.girls_for_girls.data.entity.Conference;
import com.neobis.g4g.girls_for_girls.data.entity.MentorProgram;
import com.neobis.g4g.girls_for_girls.data.entity.Training;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ApplicationDTO {
    private String fullName;

    private Timestamp dateOfBirth;

    private String email;

    private String address;

    private String workFormat;

    private String motivation;

    private String aboutMe;

    private String achievements;

    private String myFails;

    private String mySkills;

    private Training trainingId;

    private MentorProgram mentorProgramId;

    private Conference conferenceId;

    public static ApplicationDTO toApplicationDTO(Application application){
        return ApplicationDTO.builder()
                .fullName(application.getFullName())
                .dateOfBirth(application.getDateOfBirth())
                .email(application.getEmail())
                .address(application.getAddress())
                .workFormat(application.getWorkFormat())
                .motivation(application.getMotivation())
                .aboutMe(application.getAboutMe())
                .achievements(application.getAchievements())
                .myFails(application.getMyFails())
                .mySkills(application.getMySkills())
                .build();
    }

    public static List<ApplicationDTO> toApplicationDTO(List<Application> applications){
        return applications.stream().map(ApplicationDTO::toApplicationDTO).collect(Collectors.toList());
    }
}
