package com.neobis.g4g.girls_for_girls.controller;

import com.neobis.g4g.girls_for_girls.data.entity.Region;
import com.neobis.g4g.girls_for_girls.service.RegionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
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
@RequestMapping("/api/v1/regions")
@Tag(
        name = "Контроллер для получения регионов Кыргызстана",
        description = "В этом контроллере вы сможете получать регионы Кыргызстана"
)
public class RegionController {
    private final RegionService regionService;

    @Operation(
            summary = "Получить все регионы",
            tags = "Регион"
    )
    @SecurityRequirement(name = "JWT")
    @GetMapping
    public List<Region> getAllRegions() {
        return regionService.getAllRegions();
    }

    @Operation(
            summary = "Получить регион",
            description = "Позволяет получить регион по его ID",
            tags = "Регион"
    )
    @SecurityRequirement(name = "JWT")
    @GetMapping("/{id}")
    public ResponseEntity<?> getRegionById(@PathVariable
                                                        @Parameter(description = "Идентификатор региона")
                                                        Long id) {
        return regionService.getRegionById(id);
    }

    @Operation(
            summary = "Получить пользователей по айди региона",
            tags = "Категории видеокурсов"
    )
    @SecurityRequirement(name = "JWT")
    @GetMapping("/{id}/users")
    public ResponseEntity<?> getAllUsersByRegionId(@PathVariable
                                                       @Parameter(description = "Идентификатор региона")
                                                       Long id) {
        return regionService.getAllUsersByRegionId(id);
    }

}
