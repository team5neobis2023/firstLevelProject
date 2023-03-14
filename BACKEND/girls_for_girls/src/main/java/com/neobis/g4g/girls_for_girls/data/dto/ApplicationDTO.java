package com.neobis.g4g.girls_for_girls.data.dto;

import com.neobis.g4g.girls_for_girls.data.entity.Application;
import com.neobis.g4g.girls_for_girls.data.entity.Conference;
import com.neobis.g4g.girls_for_girls.data.entity.MentorProgram;
import com.neobis.g4g.girls_for_girls.data.entity.Training;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ApplicationDTO {
    @NotEmpty(message = "ФИО не может быть пустым")
    private String fullName;

    @NotNull(message = "Дата рождения не может быть пустой")
    private Timestamp dateOfBirth;

    @NotEmpty(message = "Электронная почта не может быть пустой")
    @Email(message = "Введите почту корректно")
    private String email;

    @NotEmpty(message = "Адрес не может быть пустым")
    private String address;

    private String workFormat;

    @NotEmpty(message = "Мотивационное письмо не может быть пустым")
    private String motivation;

    @NotEmpty(message = "Информация о себе не может быть пустой")
    private String aboutMe;

    @NotEmpty(message = "Достижения не могут быть пустыми")
    private String achievements;

    @NotEmpty(message = "Поле провалы не может быть пустым")
    private String myFails;

    @NotEmpty(message = "Скиллы не могут быть пустыми")
    private String mySkills;

    @NotNull(message = "Идентификатор тренинга не может быть пустым")
    private long trainingId;

    @NotNull(message = "Идентификатор менторской программы не может быть пустым")
    private long mentorProgramId;

    @NotNull(message = "Идентификатор конференции не может быть пустым")
    private long conferenceId;

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
                .mentorProgramId(application.getMentorProgram().getId())
                .trainingId(application.getTraining().getId())
                .conferenceId(application.getConference().getId())
                .build();
    }

    public static List<ApplicationDTO> toApplicationDTO(List<Application> applications){
        return applications.stream().map(ApplicationDTO::toApplicationDTO).collect(Collectors.toList());
    }
}
