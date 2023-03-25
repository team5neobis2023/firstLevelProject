package com.neobis.g4g.girls_for_girls.data.dto;

import com.neobis.g4g.girls_for_girls.data.entity.VideoCourseCategory;
import lombok.*;

import javax.validation.constraints.NotEmpty;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VideoCourseCategoryDTO {
    @NotEmpty(message = "Название категории не может быть пустым")
    private String name;

    public static VideoCourseCategoryDTO toVideoCourseCategoryDTO(VideoCourseCategory videoCourseCategory){
        return VideoCourseCategoryDTO.builder()
                .name(videoCourseCategory.getName())
                .build();
    }

    public static List<VideoCourseCategoryDTO> toVideoCourseCategoryDTO(List<VideoCourseCategory> videoCourseCategories){
        return videoCourseCategories.stream().map(VideoCourseCategoryDTO::toVideoCourseCategoryDTO).collect(Collectors.toList());
    }
}
