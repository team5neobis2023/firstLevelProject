package com.neobis.g4g.girls_for_girls.service;

import com.neobis.g4g.girls_for_girls.data.dto.ProductDTO;
import com.neobis.g4g.girls_for_girls.data.entity.Product;
import com.neobis.g4g.girls_for_girls.repository.FileRepository;
import com.neobis.g4g.girls_for_girls.repository.OrderRepo;
import com.neobis.g4g.girls_for_girls.repository.ProductRepo;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.neobis.g4g.girls_for_girls.data.dto.OrderDTO.orderToOrderDtoList;

@Service
@AllArgsConstructor
public class ProductService {

    private final ProductRepo productRepo;
    private final FileRepository fileRepo;
    private final OrderRepo orderRepo;

    public List<ProductDTO> getAllProducts() {
        return ProductDTO.productToProductDtoList(productRepo.findAll());
    }

    public ResponseEntity<?> getProductId(Long id) {
        if (productRepo.findById(id).isPresent()) {
            return ResponseEntity.ok(ProductDTO.productToProductDto(productRepo.findById(id).get()));
        }
        return new ResponseEntity<String>("Product with this id: " + id + " not found", HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<?> getAllOrdersByProductId(long id){
        if(productRepo.existsById(id)){
            return ResponseEntity.ok(orderToOrderDtoList(orderRepo.findAllByProductId(id)));
        }
        return new ResponseEntity<>("Product with id: " + id + " wasn't found", HttpStatus.NOT_FOUND);
    }


    public ResponseEntity<String> addProduct(ProductDTO productDto) {
        try {
            if (productRepo.findByTitle(productDto.getTitle()).isPresent()) {
                return ResponseEntity.badRequest().body("The product already exists");
            }
            Product product = new Product();
            product.setTitle(productDto.getTitle());
            product.setDescription(productDto.getDescription());
            product.setPrice(productDto.getPrice());
            product.setSize(productDto.getSize());
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
