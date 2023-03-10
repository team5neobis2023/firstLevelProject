package com.neobis.g4g.girls_for_girls.data.dto;

import com.neobis.g4g.girls_for_girls.data.entity.Feedback;
import com.neobis.g4g.girls_for_girls.data.entity.VideoCourse;
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
public class FeedbackDTO {
    private Timestamp recTime;

    private String fullName;

    private String email;

    private String phoneNumber;

    private String message;

    private VideoCourse videoCourse;

    public static FeedbackDTO toFeedbackDTO(Feedback feedback){
        return FeedbackDTO.builder()
                .recTime(feedback.getRecTime())
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
