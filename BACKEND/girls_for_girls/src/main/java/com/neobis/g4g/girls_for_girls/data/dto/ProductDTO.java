package com.neobis.g4g.girls_for_girls.data.dto;

import com.neobis.g4g.girls_for_girls.data.entity.File;
import com.neobis.g4g.girls_for_girls.data.entity.Product;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductDTO {
    private long id;
    private String title;
    private String description;
    private int price;
    private String size;

    public static ProductDTO productToProductDto(Product product) {
        return ProductDTO.builder()
                .id(product.getId())
                .title(product.getTitle())
                .description(product.getDescription())
                .price(product.getPrice())
                .size(product.getSize())
                .build();
    }

    public static List<ProductDTO> productToProductDtoList(List<Product> products) {
        return products.stream().map(ProductDTO::productToProductDto).collect(Collectors.toList());
    }

}
