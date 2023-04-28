package com.neobis.g4g.girls_for_girls.controller;

import com.neobis.g4g.girls_for_girls.data.dto.QuestionDTO;
import com.neobis.g4g.girls_for_girls.data.dto.SizeDTO;
import com.neobis.g4g.girls_for_girls.data.entity.Size;
import com.neobis.g4g.girls_for_girls.exception.ErrorResponse;
import com.neobis.g4g.girls_for_girls.exception.NotAddedException;
import com.neobis.g4g.girls_for_girls.service.QuestionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.PermitAll;
import javax.validation.Valid;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/questions")
@Tag(
        name = "Контроллер для управления вопросами с главной страницы",
        description = "В этом контроллере вы сможете добавлять, удалять и получать вопросы людей"
)
public class QuestionController {
    private final QuestionService questionService;

    @SecurityRequirement(name = "JWT")
    @GetMapping()
    @Operation(
            summary = "Получение всех вопросов"
    )
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<QuestionDTO> getAllQuestions(){
        return questionService.getAllQuestions();
    }

    @SecurityRequirement(name = "JWT")
    @GetMapping("/{id}")
    @Operation(
            summary = "Получение вопроса по айди"
    )
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> getQuestionById(@PathVariable("id")
                                         @Parameter(description = "Идентификатор вопроса") long id){
        return questionService.getQuestionById(id);
    }

    @Operation(
            summary = "Добавить вопрос"
    )
    @PostMapping()
    public ResponseEntity<String> addQuestion(@RequestBody @Valid QuestionDTO questionDTO,
                                          BindingResult bindingResult) {
        return questionService.addQuestion(questionDTO, bindingResult);
    }

    @SecurityRequirement(name = "JWT")
    @DeleteMapping("/{id}")
    @Operation(
            summary = "Удаление вопроса"
    )
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> deleteQuestion(@PathVariable("id")
                                           @Parameter(description = "Идентификатор вопроса") long id){
        return questionService.deleteQuestion(id);
    }

    @ExceptionHandler
    private ResponseEntity<ErrorResponse> handleException(NotAddedException e){
        ErrorResponse response = new ErrorResponse(e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
