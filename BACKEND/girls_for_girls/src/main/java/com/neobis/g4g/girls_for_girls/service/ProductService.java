package com.neobis.g4g.girls_for_girls.service;

import com.neobis.g4g.girls_for_girls.data.dto.ProductDTO;
import com.neobis.g4g.girls_for_girls.data.dto.ProductRequest;
import com.neobis.g4g.girls_for_girls.data.entity.Product;
import com.neobis.g4g.girls_for_girls.data.entity.ProductGroup;
import com.neobis.g4g.girls_for_girls.repository.ProductGroupRepo;
import com.neobis.g4g.girls_for_girls.repository.ProductRepo;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ProductService {

    private final ProductRepo productRepo;
    private final ProductGroupRepo productGroupRepo;

    public List<ProductDTO> getAllProducts() {
        List<Product> products = productRepo.findAll();
        List<ProductDTO> productDTOList = new ArrayList<>();
        for (Product product : products) {
            ProductDTO productDTO = new ProductDTO();
            productDTO.setTitle(product.getTitle());
            productDTO.setDescription(product.getDescription());
            productDTO.setSize(product.getSize());
            productDTO.setPrice(product.getPrice());
            productDTO.setTitleGroup(product.getProductGroupId().getTitle());
            productDTOList.add(productDTO);
        }
        return productDTOList;
    }

    public ResponseEntity<?> getProductId(Long id) {
        if (productRepo.findById(id).isPresent()) {
            return ResponseEntity.ok(ProductDTO.toProduct(productRepo.findById(id).get()));
        }
        return new ResponseEntity<String>("Product with this id: " + id + " not found", HttpStatus.NOT_FOUND);
    }


    public ResponseEntity<String> addProduct(ProductRequest productRequest) {
        try {
            if (productRepo.findByTitle(productRequest.getTitle()).isPresent()) {
                return ResponseEntity.badRequest().body("The product already exists");
            }
            Product product = new Product();
            Optional<ProductGroup> productGroup = productGroupRepo.findByTitle(productRequest.getProductGroup());
            product.setTitle(productRequest.getTitle());
            product.setDescription(productRequest.getDescription());
            product.setPrice(productRequest.getPrice());
            product.setSize(productRequest.getSize());
            product.setFileId(productRequest.getFile());
            product.setProductGroupId(productGroup.get());
            productRepo.save(product);
            return new ResponseEntity<String>("Product is created", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<String>("Product isn't created", HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<?> updateProduct(Long id, ProductRequest productRequest) {
        return productRepo.findById(id)
                .map(product -> {
                    product.setTitle(productRequest.getTitle());
                    product.setDescription(productRequest.getDescription());
                    product.setPrice(productRequest.getPrice());
                    product.setSize(productRequest.getSize());
                    product.setFileId(productRequest.getFile());
                    product.setProductGroupId(productGroupRepo.findByTitle(productRequest.getProductGroup()).get());
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
