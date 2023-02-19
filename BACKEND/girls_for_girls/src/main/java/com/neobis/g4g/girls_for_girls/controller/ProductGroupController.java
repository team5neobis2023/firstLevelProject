package com.neobis.g4g.girls_for_girls.controller;

import com.neobis.g4g.girls_for_girls.data.entity.ProductGroupEntity;
import com.neobis.g4g.girls_for_girls.service.ProductGroupService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/product-group")
@Tag(
        name = "Контроллер для управления записями типов товара",
        description = "В этом контроллере вы сможете добавлять, удалять, получать, а также обновлять тип товара"
)
public class ProductGroupController {

    private final ProductGroupService productGroupService;

    @Operation(summary = "Получение тип товаров", description = "Позволяет получить все типы товаров")
    @GetMapping
    public List<ProductGroupEntity> getAllProductGroups() {
        return productGroupService.getAllProductGroups();
    }

    @Operation(summary = "Получение товара", description = "Позволяет получить тип товара по его ID")
    @GetMapping("/{id}")
    public ResponseEntity<?> getProductGroupId(@PathVariable Long id) {
        return productGroupService.getProductGroupId(id);
    }



}
