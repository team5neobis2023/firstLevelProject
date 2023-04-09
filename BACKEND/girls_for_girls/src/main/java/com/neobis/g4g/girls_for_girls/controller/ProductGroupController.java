package com.neobis.g4g.girls_for_girls.controller;

import com.neobis.g4g.girls_for_girls.data.dto.ProductGroupDTO;
import com.neobis.g4g.girls_for_girls.service.ProductGroupService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @Operation(
            summary = "Получить типы товара",
            tags = "Типы товара"
    )
    @SecurityRequirement(name = "JWT")
    @GetMapping
    public List<ProductGroupDTO> getAllProductGroups() {
        return productGroupService.getAllProductGroups();
    }

    @Operation(
            summary = "Получить тип товара",
            description = "Позволяет получить тип товара по его ID",
            tags = "Типы товара"
    )
    @SecurityRequirement(name = "JWT")
    @GetMapping("/{id}")
    public ResponseEntity<?> getProductGroupId(@PathVariable
                                                   @Parameter(description = "Идентификатор типа товара")
                                                   Long id) {
        return productGroupService.getProductGroupId(id);
    }

    @Operation(
            summary = "Добавить тип товара",
            tags = "Типы товара"
    )
    @SecurityRequirement(name = "JWT")
    @PostMapping()
    public ResponseEntity<?> addProductGroup(@RequestBody ProductGroupDTO productGroupDTO) {
        return productGroupService.addProductGroup(productGroupDTO);
    }

    @Operation(
            summary = "Изменить тип товара",
            description = "Позволяет изменить тип товара по его ID",
            tags = "Типы товара"
    )
    @SecurityRequirement(name = "JWT")
    @PutMapping("/{id}")
    public ResponseEntity<?> updateProductGroup(@PathVariable
                                                    @Parameter(description = "Идентификатор типа товара")
                                                    Long id,
                                                    @RequestBody ProductGroupDTO productGroupDTO) {
        return productGroupService.updateProductGroup(id, productGroupDTO);
    }

    @Operation(
            summary = "Удалить тип товара",
            description = "Позволяет удалить тип товара по его ID",
            tags = "Типы товара"
    )
    @SecurityRequirement(name = "JWT")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProductGroup(@PathVariable
                                                         @Parameter(description = "Идентификатор типа товара")
                                                         Long id) {
        return productGroupService.deleteProductGroup(id);
    }

}
