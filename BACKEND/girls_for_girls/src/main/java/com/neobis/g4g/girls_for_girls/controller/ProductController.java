package com.neobis.g4g.girls_for_girls.controller;

import com.neobis.g4g.girls_for_girls.data.dto.ProductDTO;
import com.neobis.g4g.girls_for_girls.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/product")
@Tag(
        name = "Контроллер для управления записями товаров",
        description = "В этом контроллере вы сможете добавлять, удалять, получать, а также обновлять данные товара"
)
public class ProductController {

    private final ProductService productService;

    @GetMapping
    @Operation(
            summary = "Получить все товары",
            tags = "Товар"
    )
    private List<ProductDTO> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Получить товар",
            description = "Позволяет получить товар по его ID",
            tags = "Товар"
    )
    public ResponseEntity<?> getProductId(@PathVariable
                                              @Parameter(description = "Идентификатор товара")
                                              Long id) {
        return productService.getProductId(id);
    }

    @Operation(
            summary = "Добавить товар",
            tags = "Товар"
    )
    @PostMapping()
    public ResponseEntity<?> addProduct(@RequestBody ProductDTO productDTO) {
        return productService.addProduct(productDTO);
    }

    @Operation(
            summary = "Изменить товар",
            description = "Позволяет изменить товар по его ID",
            tags = "Товар"
    )
    @PutMapping("/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable
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
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable
                                                    @Parameter(description = "Идентификатор товара")
                                                    Long id) {
        return productService.deleteProduct(id);
    }

}
