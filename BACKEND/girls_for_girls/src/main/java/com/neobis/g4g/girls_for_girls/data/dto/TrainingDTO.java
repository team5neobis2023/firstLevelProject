package com.neobis.g4g.girls_for_girls.data.dto;

import com.neobis.g4g.girls_for_girls.data.entity.Training;
import com.neobis.g4g.girls_for_girls.data.entity.User;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TrainingDTO {
    private Timestamp recTime;

    @NotEmpty(message = "Описание не может быть пустым")
    private String description;

    @NotNull(message = "Идентификатор пользователя не может быть пустым")
    private long userId;

    private long speakerId;

    public static TrainingDTO toTrainingDTO(Training training){
        return TrainingDTO.builder()
                .recTime(training.getRecTime())
                .description(training.getDescription())
                .userId(training.getUser().getId())
                .speakerId(training.getSpeaker().getId())
                .build();
    }

    public static List<TrainingDTO> toTrainingDTO(List<Training> trainings){
        return trainings.stream().map(TrainingDTO::toTrainingDTO).collect(Collectors.toList());
    }
}
