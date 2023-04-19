package com.neobis.g4g.girls_for_girls.controller;

import com.neobis.g4g.girls_for_girls.data.dto.ProductDTO;
import com.neobis.g4g.girls_for_girls.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/product")
@Tag(
        name = "Контроллер для управления записями товаров",
        description = "В этом контроллере вы сможете добавлять, удалять, получать, а также обновлять данные товара"
)
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    @Operation(
            summary = "Получить все товары",
            tags = "Товар"
    )
    public List<ProductDTO> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Получить товар",
            description = "Позволяет получить товар по его ID",
            tags = "Товар"
    )
    public ResponseEntity<?> getProductById(@PathVariable
                                              @Parameter(description = "Идентификатор товара")
                                              Long id) {
        return productService.getProductById(id);
    }

    @GetMapping("/search")
    @Operation(
            summary = "Получить товары",
            description = "Позволяет получить товары с пагинацией и сортировкой",
            tags = "Товар"
    )
    public List<ProductDTO> getProducts(@RequestParam("page") int page,
                                         @RequestParam("size") int size,
                                         @RequestParam("sortBy") String sortBy) {
        return productService.getProducts(page, size, sortBy);
    }

    @GetMapping("/{id}/orders")
    @Operation(
            summary = "Получить заказы по айди товара",
            tags = "Товар"
    )
    @SecurityRequirement(name = "JWT")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> getAllOrdersByProductId(@PathVariable
                                          @Parameter(description = "Идентификатор товара")
                                          Long id) {
        return productService.getAllOrdersByProductId(id);
    }

    @Operation(
            summary = "Добавить товар",
            tags = "Товар"
    )
    @SecurityRequirement(name = "JWT")
    @PostMapping()
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Long> addProduct(@RequestBody ProductDTO productDTO) {
        return productService.addProduct(productDTO);
    }

    @Operation(
            summary = "Изменить товар",
            description = "Позволяет изменить товар по его ID",
            tags = "Товар"
    )
    @SecurityRequirement(name = "JWT")
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<String> updateProduct(@PathVariable
                                                @Parameter(description = "Идентификатор товара")
                                                Long id,
                                                @RequestBody ProductDTO productDTO) {
        return productService.updateProduct(id, productDTO);
    }

    @Operation(
            summary = "Удалить товар",
            description = "Позволяет удалить товар по его ID",
            tags = "Товар"
    )
    @SecurityRequirement(name = "JWT")
    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable
                                                    @Parameter(description = "Идентификатор товара")
                                                    Long id) {
        return productService.deleteProduct(id);
    }

}
