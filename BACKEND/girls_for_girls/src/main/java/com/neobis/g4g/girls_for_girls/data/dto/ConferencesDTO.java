package com.neobis.g4g.girls_for_girls.data.dto;

import com.neobis.g4g.girls_for_girls.data.entity.Application;
import com.neobis.g4g.girls_for_girls.data.entity.Conference;
import com.neobis.g4g.girls_for_girls.data.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ConferencesDTO {

    private Timestamp conferenceDate;

    private String description;

    private User userId;

    private Set<Application> applicationEntities;

    public static ConferencesDTO toConferencesDTO(Conference conference){
        return ConferencesDTO.builder()
                .conferenceDate(conference.getConferenceDate())
                .description(conference.getDescription())
                .userId(conference.getUserId())
                .applicationEntities(conference.getApplicationEntities())
                .build();
    }

    public static List<ConferencesDTO> toConferencesDTO(List<Conference> conferences){
        return conferences.stream().map(ConferencesDTO::toConferencesDTO).collect(Collectors.toList());
    }
}
