package com.neobis.g4g.girls_for_girls.controller;

import com.neobis.g4g.girls_for_girls.data.dto.ProductDTO;
import com.neobis.g4g.girls_for_girls.data.dto.RatingDTO;
import com.neobis.g4g.girls_for_girls.data.dto.TrainingDTO;
import com.neobis.g4g.girls_for_girls.data.entity.User;
import com.neobis.g4g.girls_for_girls.exception.ErrorResponse;
import com.neobis.g4g.girls_for_girls.exception.NotAddedException;
import com.neobis.g4g.girls_for_girls.exception.NotUpdatedException;
import com.neobis.g4g.girls_for_girls.service.RatingService;
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

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/ratings")
@Tag(
        name = "Контроллер для добавления рейтинга видеокурса",
        description = "В этом контроллере вы сможете добавлять рейтинги к видеокурсам"
)
public class RatingController {
    private final RatingService ratingService;

    @Operation(
            summary = "Добавить рейтинг",
            tags = "Рейтинг"
    )
    @SecurityRequirement(name = "JWT")
    @PostMapping()
    public ResponseEntity<?> addRating(@RequestBody @Valid RatingDTO ratingDTO,
                                       BindingResult bindingResult,
                                       @AuthenticationPrincipal User user) {
        return ratingService.addRating(ratingDTO, bindingResult, user);
    }

    @SecurityRequirement(name = "JWT")
    @PutMapping()
    @Operation(
            summary = "Обновление рейтинга",
            tags = "Рейтинг"
    )
    public ResponseEntity<?> updateRating(@RequestBody @Valid RatingDTO ratingDTO,
                                          BindingResult bindingResult,
                                          @AuthenticationPrincipal User user){
        return ratingService.updateRating(ratingDTO, bindingResult, user);
    }

    @ExceptionHandler
    private ResponseEntity<ErrorResponse> handleException(NotAddedException e){
        ErrorResponse response = new ErrorResponse(e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    private ResponseEntity<ErrorResponse> handleException(NotUpdatedException e){
        ErrorResponse response = new ErrorResponse(e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

}
