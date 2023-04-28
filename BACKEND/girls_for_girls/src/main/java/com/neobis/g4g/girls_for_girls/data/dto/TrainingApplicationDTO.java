package com.neobis.g4g.girls_for_girls.data.dto;

import com.neobis.g4g.girls_for_girls.data.entity.Training;
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
public class TrainingApplicationDTO {
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

    private String opportunityToCome;

    @NotBlank(message = "Причина для принятия участия не может быть пустой")
    private String reasonToParticipate;

    @NotBlank(message = "Поле Ожидания не может быть пустой")
    private String expectation;

    private String mostInterestTrainingTopic;

    private String otherInterestTopics;

    private String whereFoundTraining;

    private long training_id;

    public static TrainingApplicationDTO toTrainingApplicationDTO(TrainingApplication trainingApplication){
        return TrainingApplicationDTO.builder()
                .fullName(trainingApplication.getFullName())
                .dateOfBirth(trainingApplication.getDateOfBirth())
                .email(trainingApplication.getEmail())
                .phoneNumber(trainingApplication.getPhoneNumber())
                .address(trainingApplication.getAddress())
                .opportunityToCome(trainingApplication.getOpportunityToCome())
                .reasonToParticipate(trainingApplication.getReasonToParticipate())
                .expectation(trainingApplication.getExpectation())
                .mostInterestTrainingTopic(trainingApplication.getMostInterestTrainingTopic())
                .otherInterestTopics(trainingApplication.getOtherInterestTopics())
                .whereFoundTraining(trainingApplication.getWhereFoundTraining())
                .training_id(trainingApplication.getTraining().getId())
                .build();
    }

    public static List<TrainingApplicationDTO> toTrainingApplicationDTO(List<TrainingApplication> trainingApplications){
        return trainingApplications.stream().map(TrainingApplicationDTO::toTrainingApplicationDTO).collect(Collectors.toList());
    }
}
