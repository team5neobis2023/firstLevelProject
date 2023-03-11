package com.neobis.g4g.girls_for_girls.service;

import com.neobis.g4g.girls_for_girls.data.dto.ProductDTO;
import com.neobis.g4g.girls_for_girls.data.entity.Product;
import com.neobis.g4g.girls_for_girls.data.entity.ProductGroup;
import com.neobis.g4g.girls_for_girls.repository.ProductGroupRepo;
import com.neobis.g4g.girls_for_girls.repository.ProductRepo;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ProductService {

    private final ProductRepo productRepo;
    private final ProductGroupRepo productGroupRepo;

    public List<ProductDTO> getAllProducts() {
        return ProductDTO.productToProductDtoList(productRepo.findAll());
    }

    public ResponseEntity<?> getProductId(Long id) {
        if (productRepo.findById(id).isPresent()) {
            return ResponseEntity.ok(ProductDTO.productToProductDto(productRepo.findById(id).get()));
        }
        return new ResponseEntity<String>("Product with this id: " + id + " not found", HttpStatus.NOT_FOUND);
    }


    public ResponseEntity<String> addProduct(ProductDTO productDto) {
        try {
            if (productRepo.findByTitle(productDto.getTitle()).isPresent()) {
                return ResponseEntity.badRequest().body("The product already exists");
            }
            Product product = new Product();
            Optional<ProductGroup> productGroup = productGroupRepo.findByTitle(productDto.getTitleGroup());
            product.setTitle(productDto.getTitle());
            product.setDescription(productDto.getDescription());
            product.setPrice(productDto.getPrice());
            product.setSize(productDto.getSize());
            product.setFileId(productDto.getFile());
            product.setProductGroupId(productGroup.get());
            productRepo.save(product);
            return new ResponseEntity<String>("Product is created", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<String>("Product isn't created", HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<?> updateProduct(Long id, ProductDTO productDTO) {
        return productRepo.findById(id)
                .map(product -> {
                    product.setTitle(productDTO.getTitle());
                    product.setDescription(productDTO.getDescription());
                    product.setPrice(productDTO.getPrice());
                    product.setSize(productDTO.getSize());
                    product.setFileId(productDTO.getFile());
                    product.setProductGroupId(productGroupRepo.findByTitle(productDTO.getTitleGroup()).get());
                    productRepo.save(product);
                    return ResponseEntity.ok("Product with this id: " + id + " updated");
                }).orElse(new ResponseEntity<String>("Product with this id: " + id + " not found", HttpStatus.NOT_FOUND));
    }


    public ResponseEntity<String> deleteProduct(Long id) {
        if (productRepo.existsById(id)) {
            productRepo.deleteById(id);
            return ResponseEntity.ok("Product is deleted");
        }
        else return new ResponseEntity<String>("There is no such product", HttpStatus.NOT_FOUND);
    }
}
