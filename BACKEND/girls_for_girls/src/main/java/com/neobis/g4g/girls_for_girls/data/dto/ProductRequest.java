package com.neobis.g4g.girls_for_girls.data.dto;

import com.neobis.g4g.girls_for_girls.data.entity.File;
import lombok.Getter;

@Getter
public class ProductRequest {

    private String title;
    private String description;
    private int price;
    private String size;
    private File file;
    private String productGroup;

}
