package com.neobis.g4g.girls_for_girls.data.dto;

import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductGroupDTO {

    private String title;

    public static ProductGroupDTO ProductGroupToProductGroupDto(ProductGroup productGroup) {
        return ProductGroupDTO.builder()
                .title(productGroup.getTitle())
                .build();
    }

    public static List<ProductGroupDTO> productGroupToProductGroupDtoList(List<ProductGroup> productGroups) {
        return productGroups.stream().map(ProductGroupDTO::ProductGroupToProductGroupDto).collect(Collectors.toList());
    }

}
