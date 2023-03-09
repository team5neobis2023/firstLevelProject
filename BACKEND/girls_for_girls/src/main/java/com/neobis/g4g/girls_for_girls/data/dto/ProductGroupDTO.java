package com.neobis.g4g.girls_for_girls.data.dto;

import com.neobis.g4g.girls_for_girls.data.entity.ProductGroup;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
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
