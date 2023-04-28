package com.neobis.g4g.girls_for_girls.data.dto;

import com.neobis.g4g.girls_for_girls.data.entity.Speaker;
import com.neobis.g4g.girls_for_girls.data.entity.Training;
import lombok.*;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
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

    @NotBlank(message = "ФИО не может быть пустым")
    private String full_name;

    @NotBlank(message = "Информация не может быть пустой")
    private String full_info;

    private String image_url;

    @NotBlank(message = "instagram не может быть пустой")
    private String instagram;

    @NotBlank(message = "whatsapp не может быть пустым")
    private String whatsapp;

    @NotBlank(message = "facebook не может быть пустой")
    private String facebook;

    public static SpeakerDTO toSpeakerDTO(Speaker speaker){
        return SpeakerDTO.builder()
                .id(speaker.getId())
                .full_info(speaker.getFull_info())
                .full_name(speaker.getFull_name())
                .image_url(speaker.getImage_url())
                .instagram(speaker.getInstagram())
                .whatsapp(speaker.getWhatsapp())
                .facebook(speaker.getFacebook())
                .build();
    }

    public static List<SpeakerDTO> toSpeakerDTO(List<Speaker> speakers){
        return speakers.stream().map(SpeakerDTO::toSpeakerDTO).collect(Collectors.toList());
    }
}
