package com.neobis.g4g.girls_for_girls.controller;

import com.neobis.g4g.girls_for_girls.data.dto.FeedbackDTO;
import com.neobis.g4g.girls_for_girls.data.dto.VideoCourseDTO;
import com.neobis.g4g.girls_for_girls.data.entity.User;
import com.neobis.g4g.girls_for_girls.exception.ErrorResponse;
import com.neobis.g4g.girls_for_girls.exception.NotAddedException;
import com.neobis.g4g.girls_for_girls.exception.NotUpdatedException;
import com.neobis.g4g.girls_for_girls.service.VideoCourseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.persistence.metamodel.Bindable;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/videoCourses")
@Tag(
        name = "Контроллер для управления видеокурсами",
        description = "В этом контроллере вы можете добавлять, удалять, получать и обновлять данные видеокурса"
)
public class VideoCourseController {
    private final VideoCourseService videoCourseService;

    @Autowired
    public VideoCourseController(VideoCourseService videoCourseService) {
        this.videoCourseService = videoCourseService;
    }

    @GetMapping()
    @Operation(
            summary = "Получение всех видеокурсов"
    )
    public List<VideoCourseDTO> getAllVideoCourses(){
        return videoCourseService.getAllVideoCourses();
    }

    @SecurityRequirement(name = "JWT")
    @GetMapping("/{id}")
    @Operation(
            summary = "Получение видеокурса по айди"
    )
    public ResponseEntity<?> getVideoCourseById(@PathVariable("id")
                                             @Parameter(description = "Идентификатор видеокурса") long id){
        return videoCourseService.getVideoCourseById(id);
    }

    @GetMapping("/{id}/feedbacks")
    @Operation(
            summary = "Получение отзывов по айди видеокурса"
    )
    public ResponseEntity<?> getAllFeedbacksByVideoCourseId(@PathVariable("id")
                                                            @Parameter(description = "Идентификатор видеокурса") long id){
        return videoCourseService.getAllFeedbacksByVideoCourseId(id);
    }

    @SecurityRequirement(name = "JWT")
    @PostMapping()
    @Operation(
            summary = "Добавление видеокурса"
    )
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<String> addVideoCourse(@RequestBody @Valid VideoCourseDTO videoCourseDTO,
                                                 BindingResult bindingResult,
                                                 @AuthenticationPrincipal User user){
        return videoCourseService.addVideoCourse(videoCourseDTO, bindingResult, user);
    }

    @SecurityRequirement(name = "JWT")
    @PutMapping("/{id}")
    @Operation(
            summary = "Обновление данных видеокурса по айди"
    )
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<String> updateVideoCourse(@PathVariable("id")
                                               @Parameter(description = "Идентификатор видеокурса") long id,
                                               @RequestBody @Valid VideoCourseDTO videoCourseDTO,
                                               BindingResult bindingResult,
                                                    @AuthenticationPrincipal User user){
        return videoCourseService.updateVideoCourse(id, videoCourseDTO, bindingResult, user);
    }

    @SecurityRequirement(name = "JWT")
    @DeleteMapping("/{id}")
    @Operation(
            summary = "Удаление видеокурса по айди"
    )
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<String> deleteVideoCourse(@PathVariable("id")
                                                   @Parameter(description = "Идентификатор видеокурса") long id){
        return videoCourseService.deleteVideoCourse(id);
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
