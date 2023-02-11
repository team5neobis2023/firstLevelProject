package com.neobis.g4g.girls_for_girls.controller;

import com.neobis.g4g.girls_for_girls.data.entity.ProductEntity;
import com.neobis.g4g.girls_for_girls.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
            summary = "Получение всех товаров",
            description = "Позволяет получить всех товаров"
    )
    private List<ProductEntity> getAllProducts() {
        return productService.getAllProducts();
    }

}
