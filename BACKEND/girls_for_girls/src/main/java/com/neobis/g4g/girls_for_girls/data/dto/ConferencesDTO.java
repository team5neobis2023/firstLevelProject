package com.neobis.g4g.girls_for_girls.data.dto;

import com.neobis.g4g.girls_for_girls.data.entity.Application;
import com.neobis.g4g.girls_for_girls.data.entity.Conference;
import com.neobis.g4g.girls_for_girls.data.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.sql.Timestamp;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ConferencesDTO {

    @NotNull(message = "Дата конференции не может быть пустой")
    private Timestamp conferenceDate;

    @NotEmpty(message = "Описание не может быть пустым")
    @Size(min = 5, message = "Описание должно содержать от 5 символов")
    private String description;

    @NotNull(message = "Идентификатор пользователя не может быть пустым")
    private long userId;

    public static ConferencesDTO toConferencesDTO(Conference conference){
        return ConferencesDTO.builder()
                .conferenceDate(conference.getConferenceDate())
                .description(conference.getDescription())
                .userId(conference.getUserId().getId())
                .build();
    }

    public static List<ConferencesDTO> toConferencesDTO(List<Conference> conferences){
        return conferences.stream().map(ConferencesDTO::toConferencesDTO).collect(Collectors.toList());
    }
}
