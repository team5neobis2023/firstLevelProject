package com.neobis.g4g.girls_for_girls.controller;

import com.neobis.g4g.girls_for_girls.data.dto.RatingDTO;
import com.neobis.g4g.girls_for_girls.data.dto.SizeDTO;
import com.neobis.g4g.girls_for_girls.data.dto.SpeakerDTO;
import com.neobis.g4g.girls_for_girls.data.entity.Size;
import com.neobis.g4g.girls_for_girls.data.entity.User;
import com.neobis.g4g.girls_for_girls.exception.ErrorResponse;
import com.neobis.g4g.girls_for_girls.exception.NotAddedException;
import com.neobis.g4g.girls_for_girls.service.SizeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/sizes")
@Tag(
        name = "Контроллер для работы с размерами продуктов",
        description = "В этом контроллере вы сможете получать, добавлять и удалять размеры"
)
public class SizeController {
    private final SizeService sizeService;

    @SecurityRequirement(name = "JWT")
    @GetMapping()
    @Operation(
            summary = "Получение всех размеров",
            tags = "Размер"
    )
    public List<Size> getAllSizes(){
        return sizeService.getAllSizes();
    }

    @SecurityRequirement(name = "JWT")
    @GetMapping("/{id}")
    @Operation(
            summary = "Получение размера по айди",
            tags = "Размер"
    )
    public ResponseEntity<?> getSizeById(@PathVariable("id")
                                            @Parameter(description = "Идентификатор размера") long id){
        return sizeService.getSizeById(id);
    }

    @Operation(
            summary = "Добавить размер",
            tags = "Размер"
    )
    @SecurityRequirement(name = "JWT")
    @PostMapping()
    public ResponseEntity<String> addSize(@RequestBody @Valid SizeDTO sizeDTO,
                                            BindingResult bindingResult) {
        return sizeService.addSize(sizeDTO, bindingResult);
    }

    @SecurityRequirement(name = "JWT")
    @DeleteMapping("/{id}")
    @Operation(
            summary = "Удаление размера",
            tags = "Размер"
    )
    public ResponseEntity<?> deleteSpeaker(@PathVariable("id")
                                           @Parameter(description = "Идентификатор размера") long id){
        return sizeService.deleteSize(id);
    }

    @ExceptionHandler
    private ResponseEntity<ErrorResponse> handleException(NotAddedException e){
        ErrorResponse response = new ErrorResponse(e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
