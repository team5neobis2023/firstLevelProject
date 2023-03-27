package com.neobis.g4g.girls_for_girls.data.dto;

import com.neobis.g4g.girls_for_girls.data.entity.Region;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegionDTO {
    private String name;

    public static RegionDTO toRegionDTO(Region region){
        return RegionDTO.builder()
                .name(region.getName())
                .build();
    }

    public static List<RegionDTO> toRegionDTO(List<Region> regions){
        return regions.stream().map(RegionDTO::toRegionDTO).collect(Collectors.toList());
    }
}
