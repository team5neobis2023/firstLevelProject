package com.neobis.g4g.girls_for_girls.service;

import com.neobis.g4g.girls_for_girls.data.entity.ProductEntity;
import com.neobis.g4g.girls_for_girls.repository.ProductRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ProductService {

    private final ProductRepo productRepo;

    public List<ProductEntity> getAllProducts() {
        return productRepo.findAll();
    }
}
