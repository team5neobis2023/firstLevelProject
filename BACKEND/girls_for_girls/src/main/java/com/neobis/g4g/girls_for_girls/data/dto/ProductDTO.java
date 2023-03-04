package com.neobis.g4g.girls_for_girls.data.dto;

import com.neobis.g4g.girls_for_girls.data.entity.Product;
import lombok.Data;

@Data
public class ProductDTO {

    private String title;
    private String description;
    private int price;
    private String size;
    private String titleGroup;

    public static ProductDTO toProduct(Product product) {

        ProductDTO productDTO = new ProductDTO();
        productDTO.setTitle(product.getTitle());
        productDTO.setDescription(product.getDescription());
        productDTO.setPrice(product.getPrice());
        productDTO.setSize(product.getSize());
        productDTO.setTitleGroup(product.getProductGroupId().getTitle());
        return productDTO;
    }



}
