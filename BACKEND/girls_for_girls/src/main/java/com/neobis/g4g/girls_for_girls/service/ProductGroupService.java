package com.neobis.g4g.girls_for_girls.service;

import com.neobis.g4g.girls_for_girls.data.dto.ProductGroupDTO;
import com.neobis.g4g.girls_for_girls.data.dto.ProductGroupRequest;
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
            return ResponseEntity.ok(ProductGroupDTO.toProductGroup(productGroupRepo.findById(id).get()));
        }
        return new ResponseEntity<String>("Product with this id: " + id + " not found", HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<String> addProductGroup(ProductGroupRequest productGroupRequest) {
        try {
            if (productGroupRepo.findByTitle(productGroupRequest.getTitle()).isPresent()) {
                return ResponseEntity.badRequest().body("The product group already exists");
            }
            ProductGroupEntity productGroup = new ProductGroupEntity(productGroupRequest.getTitle());
            productGroupRepo.save(productGroup);
            return new ResponseEntity<String>("Product group is created", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<String>("Product isn't created", HttpStatus.BAD_REQUEST);
        }
    }


    public ResponseEntity<?> updateProductGroup(Long id, ProductGroupDTO productGroupDTO) {
        return productGroupRepo.findById(id)
                .map(productGroup -> {
                    productGroup.setTitle(productGroupDTO.getTitle());
                    productGroupRepo.save(productGroup);
                    return ResponseEntity.ok("Product group with this id " + id + " updated");
                }).orElse(new ResponseEntity<String>("Product group with this id " + id + " not found", HttpStatus.NOT_FOUND));
    }

    public ResponseEntity<String> deleteProductGroup(Long id) {
        if (productGroupRepo.existsById(id)) {
            productGroupRepo.deleteById(id);
            return ResponseEntity.ok("Product group is deleted");
        }
        else return new ResponseEntity<String>("There is no such product group", HttpStatus.NOT_FOUND);
    }
}
