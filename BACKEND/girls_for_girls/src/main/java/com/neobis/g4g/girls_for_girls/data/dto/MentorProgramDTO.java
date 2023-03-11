package com.neobis.g4g.girls_for_girls.data.dto;

import com.neobis.g4g.girls_for_girls.data.entity.Application;
import com.neobis.g4g.girls_for_girls.data.entity.MentorProgram;
import com.neobis.g4g.girls_for_girls.data.entity.User;
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
public class MentorProgramDTO {
    private String description;

    private User userId;

    private Timestamp recTime;

    private List<Application> applicationEntities;

    public static MentorProgramDTO toMentorProgramDTO(MentorProgram mentorProgram){
        return MentorProgramDTO.builder()
                .description(mentorProgram.getDescription())
                .applicationEntities(mentorProgram.getApplicationEntities())
                .recTime(mentorProgram.getRecTime())
                .userId(mentorProgram.getUserId())
                .build();
    }

    public static List<MentorProgramDTO> toMentorProgramDTO(List<MentorProgram> mentorPrograms){
        return mentorPrograms.stream().map(MentorProgramDTO::toMentorProgramDTO).collect(Collectors.toList());
    }
}
