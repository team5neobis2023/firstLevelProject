package com.neobis.g4g.girls_for_girls.controller;

import com.neobis.g4g.girls_for_girls.data.dto.ProductGroupDTO;
import com.neobis.g4g.girls_for_girls.data.dto.VideoCourseCategoryDTO;
import com.neobis.g4g.girls_for_girls.data.entity.VideoCourseCategory;
import com.neobis.g4g.girls_for_girls.exception.ErrorResponse;
import com.neobis.g4g.girls_for_girls.exception.NotAddedException;
import com.neobis.g4g.girls_for_girls.exception.NotUpdatedException;
import com.neobis.g4g.girls_for_girls.service.ProductGroupService;
import com.neobis.g4g.girls_for_girls.service.VideoCourseCategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/videoCourseCategories")
@Tag(
        name = "Контроллер для управления записями категорий видеоуроков",
        description = "В этом контроллере вы сможете добавлять, удалять, получать, а также обновлять категории видеоуроков"
)
public class VideoCourseCategoryController {
    private final VideoCourseCategoryService videoCourseCategoryService;

    @Operation(
            summary = "Получить категории видеокурсов",
            tags = "Категории видеокурсов"
    )
    @SecurityRequirement(name = "JWT")
    @GetMapping
    public List<VideoCourseCategoryDTO> getAllVideoCourseCategories() {
        return videoCourseCategoryService.getAllVideoCourseCategories();
    }

    @Operation(
            summary = "Получить категорию видеокурса",
            description = "Позволяет получить категорию по ее ID",
            tags = "Категории видеокурсов"
    )
    @SecurityRequirement(name = "JWT")
    @GetMapping("/{id}")
    public ResponseEntity<?> getVideoCourseCategoryById(@PathVariable
                                               @Parameter(description = "Идентификатор категории")
                                               Long id) {
        return videoCourseCategoryService.getVideoCourseCategoryById(id);
    }

    @Operation(
            summary = "Получить видеокурсы по айди категории",
            tags = "Категории видеокурсов"
    )
    @SecurityRequirement(name = "JWT")
    @GetMapping("/{id}/videoCourses")
    public ResponseEntity<?> getAllVideoCoursesByVideoCourseCategoryId(@PathVariable
                                                        @Parameter(description = "Идентификатор категории")
                                                        Long id) {
        return videoCourseCategoryService.getAllVideoCoursesByVideoCourseCategoryId(id);
    }

    @Operation(
            summary = "Добавить категорию видеокурсов",
            tags = "Категории видеокурсов"
    )
    @SecurityRequirement(name = "JWT")
    @PostMapping()
    public ResponseEntity<String> addVideoCourseCategory(@RequestBody @Valid VideoCourseCategoryDTO videoCourseCategoryDTO,
                                                    BindingResult bindingResult) {
        return videoCourseCategoryService.addVideoCourseCategory(videoCourseCategoryDTO, bindingResult);
    }

    @Operation(
            summary = "Изменить категорию видеокурса",
            description = "Позволяет изменить категорию по ее ID",
            tags = "Категории видеокурсов"
    )
    @SecurityRequirement(name = "JWT")
    @PutMapping("/{id}")
    public ResponseEntity<String> updateVideoCourseCategory(@PathVariable
                                                @Parameter(description = "Идентификатор категории") Long id,
                                                @RequestBody @Valid VideoCourseCategoryDTO videoCourseCategoryDTO,
                                                BindingResult bindingResult) {
        return videoCourseCategoryService.updateVideoCourseCategory(id, videoCourseCategoryDTO, bindingResult);
    }

    @Operation(
            summary = "Удалить категорию видеокурсов",
            description = "Позволяет удалить категорию по ее ID",
            tags = "Типы товара"
    )
    @SecurityRequirement(name = "JWT")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteVideoCourseCategoryById(@PathVariable
                                                     @Parameter(description = "Идентификатор категории")
                                                     Long id) {
        return videoCourseCategoryService.deleteVideoCourseCategoryById(id);
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
