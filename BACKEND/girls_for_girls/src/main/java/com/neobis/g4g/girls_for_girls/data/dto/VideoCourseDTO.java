package com.neobis.g4g.girls_for_girls.data.dto;

import com.neobis.g4g.girls_for_girls.data.entity.Feedback;
import com.neobis.g4g.girls_for_girls.data.entity.User;
import com.neobis.g4g.girls_for_girls.data.entity.VideoCourse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VideoCourseDTO {

    private Timestamp recTime;

    private String description;

    private int rating;

    private long userId;

    public static VideoCourseDTO toVideoCourseDTO(VideoCourse videoCourse){
        return VideoCourseDTO.builder()
                .description(videoCourse.getDescription())
                .userId(videoCourse.getUserId().getId())
                .recTime(videoCourse.getRecTime())
                .rating(videoCourse.getRating())
                .build();
    }

    public static List<VideoCourseDTO> toVideoCourseDTO(List<VideoCourse> videoCourses){
        return videoCourses.stream().map(VideoCourseDTO::toVideoCourseDTO).collect(Collectors.toList());
    }
}
