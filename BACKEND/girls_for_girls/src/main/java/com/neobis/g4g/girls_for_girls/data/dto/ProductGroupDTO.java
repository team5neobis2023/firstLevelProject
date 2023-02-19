package com.neobis.g4g.girls_for_girls.data.dto;

import com.neobis.g4g.girls_for_girls.data.entity.ProductGroupEntity;
import lombok.Data;

@Data
public class ProductGroupDTO {

    private String title;

    public static ProductGroupDTO toProductGroup(ProductGroupEntity productGroup) {
        ProductGroupDTO productGroupDTO = new ProductGroupDTO();
        productGroupDTO.setTitle(productGroupDTO.getTitle());
        return productGroupDTO;
    }

}
