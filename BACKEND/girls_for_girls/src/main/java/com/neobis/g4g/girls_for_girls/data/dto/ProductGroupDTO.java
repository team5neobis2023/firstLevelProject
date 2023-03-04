package com.neobis.g4g.girls_for_girls.data.dto;

import com.neobis.g4g.girls_for_girls.data.entity.ProductGroup;
import lombok.Data;

@Data
public class ProductGroupDTO {

    private String title;

    public static ProductGroupDTO toProductGroup(ProductGroup productGroup) {
        ProductGroupDTO productGroupDTO = new ProductGroupDTO();
        productGroupDTO.setTitle(productGroup.getTitle());
        return productGroupDTO;
    }

}
