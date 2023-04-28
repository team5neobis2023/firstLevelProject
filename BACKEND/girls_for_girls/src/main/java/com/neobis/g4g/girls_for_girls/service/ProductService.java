package com.neobis.g4g.girls_for_girls.service;

import com.neobis.g4g.girls_for_girls.data.dto.ProductDTO;
import com.neobis.g4g.girls_for_girls.data.entity.Product;
import com.neobis.g4g.girls_for_girls.repository.OrderRepo;
import com.neobis.g4g.girls_for_girls.repository.ProductRepo;
import com.neobis.g4g.girls_for_girls.repository.SizeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.neobis.g4g.girls_for_girls.data.dto.OrderDTO.orderToOrderDtoList;
import static com.neobis.g4g.girls_for_girls.data.dto.ProductDTO.productToProductDto;
import static com.neobis.g4g.girls_for_girls.data.dto.ProductDTO.productToProductDtoList;

@Service
public class ProductService {

    private final ProductRepo productRepo;
    private final OrderRepo orderRepo;
    private final SizeRepository sizeRepository;

    @Autowired
    public ProductService(ProductRepo productRepo, OrderRepo orderRepo, SizeRepository sizeRepository) {
        this.productRepo = productRepo;
        this.orderRepo = orderRepo;
        this.sizeRepository = sizeRepository;
    }

    public List<ProductDTO> getAllProducts() {
        return productToProductDtoList(productRepo.findAll());
    }

    public Page<Product> getProducts(Pageable pageable) {
        return productRepo.findAll(pageable);
    }

    public ResponseEntity<?> getProductById(Long id) {
        if (productRepo.findById(id).isPresent()) {
           return ResponseEntity.ok(productToProductDto(productRepo.findById(id).get()));
        }
        return new ResponseEntity<>("Product with this id: " + id + " not found", HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<?> getAllOrdersByProductId(long id){
        if(productRepo.existsById(id)){
            return ResponseEntity.ok(orderToOrderDtoList(orderRepo.findAllByProductId(id)));
        }
        return new ResponseEntity<>("Product with id: " + id + " wasn't found", HttpStatus.NOT_FOUND);
    }


    public ResponseEntity<Long> addProduct(ProductDTO productDto) {
        try {
            if (productRepo.findByTitle(productDto.getTitle()).isPresent()) {
                return ResponseEntity.badRequest().body(0L);
            }
            Product product = new Product();
            product.setTitle(productDto.getTitle());
            product.setDescription(productDto.getDescription());
            product.setPrice(productDto.getPrice());
            product.setSizes(sizeRepository.findAll());
            return ResponseEntity.ok(productRepo.save(product).getId());
        } catch (Exception e) {
            return new ResponseEntity<>(0L , HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<String> updateProduct(Long id, ProductDTO productDTO) {
        return productRepo.findById(id)
                .map(product -> {
                    product.setTitle(productDTO.getTitle());
                    product.setDescription(productDTO.getDescription());
                    product.setPrice(productDTO.getPrice());
                    product.setSizes(sizeRepository.findAll());
                    productRepo.save(product);
                    return ResponseEntity.ok("Product with this id: " + id + " updated");
                }).orElse(new ResponseEntity<>("Product with this id: " + id + " not found", HttpStatus.NOT_FOUND));
    }


    public ResponseEntity<String> deleteProduct(Long id) {
        if (productRepo.existsById(id)) {
            productRepo.deleteById(id);
            return ResponseEntity.ok("Product is deleted");
        }
        else return new ResponseEntity<>("There is no such product", HttpStatus.NOT_FOUND);
    }

}
