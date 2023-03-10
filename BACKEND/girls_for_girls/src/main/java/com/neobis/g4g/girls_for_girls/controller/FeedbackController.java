package com.neobis.g4g.girls_for_girls.controller;

import com.neobis.g4g.girls_for_girls.data.dto.FeedbackDTO;
import com.neobis.g4g.girls_for_girls.data.entity.Feedback;
import com.neobis.g4g.girls_for_girls.service.FeedbackService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping()
    @Operation(
            summary = "Получение всех отзывов",
            tags = "Отзыв"
    )
    public List<FeedbackDTO> getAllFeedbacks(){
        return feedbackService.getAllFeedbacks();
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Получение отзыва по айди",
            tags = "Отзыв"
    )
    public ResponseEntity<?> getFeedbackById(@PathVariable("id")
                                             @Parameter(description = "Идентификатор отзыва") long id){
        return feedbackService.getFeedbackById(id);
    }

    @PostMapping()
    @Operation(
            summary = "Добавление отзыва",
            tags = "Отзыв"
    )
    public ResponseEntity<?> addFeedback(@RequestBody FeedbackDTO feedbackDTO){
        return feedbackService.addFeedback(feedbackDTO);
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Обновление данных отзыва",
            tags = "Отзыв"
    )
    public ResponseEntity<?> updateFeedback(@PathVariable("id")
                                            @Parameter(description = "Идентификатор отзыва") long id,
                                            @RequestBody FeedbackDTO feedbackDTO){
        return feedbackService.updateFeedback(id, feedbackDTO);
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Удаление отзыва",
            tags = "Отзыв"
    )
    public ResponseEntity<?> deleteFeedback(@PathVariable("id")
                                            @Parameter(description = "Идентификатор отзыва") long id){
        return feedbackService.deleteFeedback(id);
    }

}
