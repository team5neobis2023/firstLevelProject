package com.neobis.g4g.girls_for_girls.data.dto;

import com.neobis.g4g.girls_for_girls.data.entity.Application;
import com.neobis.g4g.girls_for_girls.data.entity.MentorProgram;
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
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MentorProgramDTO {
    @NotEmpty(message = "Описание не может быть пустым")
    @Size(min = 5, message = "Описание должно содержать минимум 5 символов")
    private String description;

    @NotNull(message = "Идентификатор пользователя не может быть пустым")
    private long userId;

    private Timestamp recTime;


    public static MentorProgramDTO toMentorProgramDTO(MentorProgram mentorProgram){
        return MentorProgramDTO.builder()
                .description(mentorProgram.getDescription())
                .recTime(mentorProgram.getRecTime())
                .userId(mentorProgram.getUserId().getId())
                .build();
    }

    public static List<MentorProgramDTO> toMentorProgramDTO(List<MentorProgram> mentorPrograms){
        return mentorPrograms.stream().map(MentorProgramDTO::toMentorProgramDTO).collect(Collectors.toList());
    }
}
