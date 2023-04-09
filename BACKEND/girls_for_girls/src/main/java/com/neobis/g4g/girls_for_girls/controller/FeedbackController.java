package com.neobis.g4g.girls_for_girls.controller;

import com.neobis.g4g.girls_for_girls.data.dto.FeedbackDTO;
import com.neobis.g4g.girls_for_girls.data.entity.Feedback;
import com.neobis.g4g.girls_for_girls.data.entity.User;
import com.neobis.g4g.girls_for_girls.exception.ErrorResponse;
import com.neobis.g4g.girls_for_girls.exception.NotAddedException;
import com.neobis.g4g.girls_for_girls.exception.NotUpdatedException;
import com.neobis.g4g.girls_for_girls.service.FeedbackService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/feedbacks")
@Tag(
        name = "Контроллер для управления отзывами",
        description = "В этом контроллере вы можете добавлять, удалять, получать и обновлять данные отзыва"
)
public class FeedbackController {
    private final FeedbackService feedbackService;

    @Autowired
    public FeedbackController(FeedbackService feedbackService) {
        this.feedbackService = feedbackService;
    }

    @SecurityRequirement(name = "JWT")
    @GetMapping()
    @Operation(
            summary = "Получение всех отзывов",
            tags = "Отзыв"
    )
    public List<FeedbackDTO> getAllFeedbacks(){
        return feedbackService.getAllFeedbacks();
    }

    @SecurityRequirement(name = "JWT")
    @GetMapping("/{id}")
    @Operation(
            summary = "Получение отзыва по айди",
            tags = "Отзыв"
    )
    public ResponseEntity<?> getFeedbackById(@PathVariable("id")
                                             @Parameter(description = "Идентификатор отзыва") long id){
        return feedbackService.getFeedbackById(id);
    }

    @SecurityRequirement(name = "JWT")
    @PostMapping()
    @Operation(
            summary = "Добавление отзыва",
            tags = "Отзыв"
    )
    public ResponseEntity<?> addFeedback(@RequestBody @Valid FeedbackDTO feedbackDTO,
                                         BindingResult bindingResult,
                                         @AuthenticationPrincipal User user){
        return feedbackService.addFeedback(feedbackDTO, bindingResult, user);
    }

    @SecurityRequirement(name = "JWT")
    @PutMapping("/{id}")
    @Operation(
            summary = "Обновление данных отзыва",
            tags = "Отзыв"
    )
    public ResponseEntity<?> updateFeedback(@PathVariable("id")
                                            @Parameter(description = "Идентификатор отзыва") long id,
                                            @RequestBody @Valid FeedbackDTO feedbackDTO,
                                            BindingResult bindingResult,
                                            @AuthenticationPrincipal User user){
        return feedbackService.updateFeedback(id, feedbackDTO, bindingResult, user);
    }

    @SecurityRequirement(name = "JWT")
    @DeleteMapping("/{id}")
    @Operation(
            summary = "Удаление отзыва",
            tags = "Отзыв"
    )
    public ResponseEntity<?> deleteFeedback(@PathVariable("id")
                                            @Parameter(description = "Идентификатор отзыва") long id){
        return feedbackService.deleteFeedback(id);
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
