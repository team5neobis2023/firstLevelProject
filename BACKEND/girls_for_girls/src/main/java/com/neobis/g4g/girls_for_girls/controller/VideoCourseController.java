package com.neobis.g4g.girls_for_girls.controller;

import com.neobis.g4g.girls_for_girls.data.dto.FeedbackDTO;
import com.neobis.g4g.girls_for_girls.data.dto.VideoCourseDTO;
import com.neobis.g4g.girls_for_girls.exception.ErrorResponse;
import com.neobis.g4g.girls_for_girls.exception.NotAddedException;
import com.neobis.g4g.girls_for_girls.exception.NotUpdatedException;
import com.neobis.g4g.girls_for_girls.service.VideoCourseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
            summary = "Получение всех видеокурсов",
            tags = "Видеокурс"
    )
    public List<VideoCourseDTO> getAllVideoCourses(){
        return videoCourseService.getAllVideoCourses();
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Получение видеокурса по айди",
            tags = "Видеокурс"
    )
    public ResponseEntity<?> getVideoCourseById(@PathVariable("id")
                                             @Parameter(description = "Идентификатор видеокурса") long id){
        return videoCourseService.getVideoCourseById(id);
    }

    @GetMapping("/{id}/feedbacks")
    @Operation(
            summary = "Получение отзывов по айди видеокурса",
            tags = "Видеокурс"
    )
    public ResponseEntity<?> getAllFeedbacksByVideoCourseId(@PathVariable("id")
                                                            @Parameter(description = "Идентификатор видеокурса") long id){
        return videoCourseService.getAllFeedbacksByVideoCourseId(id);
    }

    @PostMapping()
    @Operation(
            summary = "Добавление видеокурса",
            tags = "Видеокурс"
    )
    public ResponseEntity<String> addVideoCourse(@RequestBody @Valid VideoCourseDTO videoCourseDTO,
                                            BindingResult bindingResult){
        return videoCourseService.addVideoCourse(videoCourseDTO, bindingResult);
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Обновление данных видеокурса по айди",
            tags = "Видеокурс"
    )
    public ResponseEntity<String> updateVideoCourse(@PathVariable("id")
                                               @Parameter(description = "Идентификатор видеокурса") long id,
                                               @RequestBody @Valid VideoCourseDTO videoCourseDTO,
                                               BindingResult bindingResult){
        return videoCourseService.updateVideoCourse(id, videoCourseDTO, bindingResult);
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Удаление видеокурса по айди",
            tags = "Видеокурс"
    )
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
