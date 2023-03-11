package com.neobis.g4g.girls_for_girls.data.dto;

import com.neobis.g4g.girls_for_girls.data.entity.Feedback;
import com.neobis.g4g.girls_for_girls.data.entity.VideoCourse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FeedbackDTO {
    @NotEmpty(message = "ФИО не может быть пустым")
    private String fullName;

    @NotEmpty(message = "Электронная почта не может быть пустой")
    @Email(message = "Введите почту корректно")
    private String email;

    @NotEmpty(message = "Номер телефона не может быть пустым")
    private String phoneNumber;

    @NotEmpty(message = "Отзыв не может быть пустым")
    @Size(min = 3, message = "Отзыв должен содержать от 3 символов")
    private String message;

    private VideoCourse videoCourse;

    public static FeedbackDTO toFeedbackDTO(Feedback feedback){
        return FeedbackDTO.builder()
                .fullName(feedback.getFullName())
                .email(feedback.getEmail())
                .message(feedback.getMessage())
                .phoneNumber(feedback.getPhoneNumber())
                .videoCourse(feedback.getVideoCourse())
                .build();
    }

    public static List<FeedbackDTO> toFeedbackDTO(List<Feedback> feedbacks){
        return feedbacks.stream().map(FeedbackDTO::toFeedbackDTO).collect(Collectors.toList());
    }
}
