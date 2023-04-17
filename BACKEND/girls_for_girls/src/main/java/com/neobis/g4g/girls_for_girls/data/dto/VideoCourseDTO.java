package com.neobis.g4g.girls_for_girls.data.dto;

import com.neobis.g4g.girls_for_girls.data.entity.Feedback;
import com.neobis.g4g.girls_for_girls.data.entity.User;
import com.neobis.g4g.girls_for_girls.data.entity.VideoCourse;
import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VideoCourseDTO {

    private Timestamp recTime;

    private String description;

    private int rating;

    private long videoCourseCategoryId;

    private long speakerId;

    private String video_url;

    public static VideoCourseDTO toVideoCourseDTO(VideoCourse videoCourse){
        return VideoCourseDTO.builder()
                .speakerId(videoCourse.getSpeaker().getId())
                .description(videoCourse.getDescription())
                .recTime(videoCourse.getRecTime())
                .rating(videoCourse.getRating())
                .videoCourseCategoryId(videoCourse.getVideoCourseCategory().getId())
                .video_url(videoCourse.getVideo_url())
                .build();
    }

    public static List<VideoCourseDTO> toVideoCourseDTO(List<VideoCourse> videoCourses){
        return videoCourses.stream().map(VideoCourseDTO::toVideoCourseDTO).collect(Collectors.toList());
    }
}
