package com.neobis.g4g.girls_for_girls.data.dto;

import com.neobis.g4g.girls_for_girls.data.entity.Speaker;
import com.neobis.g4g.girls_for_girls.data.entity.Training;
import lombok.*;

import javax.validation.constraints.NotEmpty;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SpeakerDTO {
    private long id;

    @NotEmpty(message = "ФИО не может быть пустым")
    private String full_name;

    @NotEmpty(message = "Информация не может быть пустой")
    private String full_info;

    public static SpeakerDTO toSpeakerDTO(Speaker speaker){
        return SpeakerDTO.builder()
                .id(speaker.getId())
                .full_info(speaker.getFull_info())
                .full_name(speaker.getFull_name())
                .build();
    }

    public static List<SpeakerDTO> toSpeakerDTO(List<Speaker> speakers){
        return speakers.stream().map(SpeakerDTO::toSpeakerDTO).collect(Collectors.toList());
    }
}
