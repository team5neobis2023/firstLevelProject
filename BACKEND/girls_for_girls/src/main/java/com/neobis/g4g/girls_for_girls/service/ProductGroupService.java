package com.neobis.g4g.girls_for_girls.service;

import com.neobis.g4g.girls_for_girls.data.entity.ProductGroupEntity;
import com.neobis.g4g.girls_for_girls.repository.ProductGroupRepo;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ProductGroupService {

    private final ProductGroupRepo productGroupRepo;

    public List<ProductGroupEntity> getAllProductGroups() {
        return productGroupRepo.findAll();
    }

    public ResponseEntity<?> getProductGroupId(Long id) {
        if (productGroupRepo.findById(id).isPresent()) {
            return ResponseEntity.ok(productGroupRepo.findById(id));
        }
        return new ResponseEntity<String>("Product with this id: " + id + " not found", HttpStatus.NOT_FOUND);
    }
}
