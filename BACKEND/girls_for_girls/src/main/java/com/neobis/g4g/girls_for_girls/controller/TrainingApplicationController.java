package com.neobis.g4g.girls_for_girls.controller;

import com.neobis.g4g.girls_for_girls.data.dto.TrainingApplicationDTO;
import com.neobis.g4g.girls_for_girls.data.entity.TrainingApplication;
import com.neobis.g4g.girls_for_girls.data.entity.User;
import com.neobis.g4g.girls_for_girls.exception.ErrorResponse;
import com.neobis.g4g.girls_for_girls.exception.NotAddedException;
import com.neobis.g4g.girls_for_girls.exception.NotUpdatedException;
import com.neobis.g4g.girls_for_girls.service.TrainingApplicationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/training-application")
@Tag(
        name = "Контроллер для управления заявками на тренинги",
        description = "В этом контроллере вы можете добавлять, удалять и получать данные заявок на тренинги"
)
@AllArgsConstructor
public class TrainingApplicationController {
    private final TrainingApplicationService trainingApplicationService;

    @SecurityRequirement(name = "JWT")
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping()
    @Operation(
            summary = "Получение всех заявок"
    )
    public Page<TrainingApplication> getAllApplications(@PageableDefault Pageable pageable){
        return trainingApplicationService.getAllApplications(pageable);
    }

    @SecurityRequirement(name = "JWT")
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/{id}")
    @Operation(
            summary = "Получение заявки",
            description = "Получение заявки по конкретному айди"
    )
    public ResponseEntity<?> getApplicationById(@PathVariable("id")
                                                 @Parameter(description = "Идентификатор заявки")
                                                 long id){
        return trainingApplicationService.getApplicationById(id);
    }

    @SecurityRequirement(name = "JWT")
    @PostMapping()
    @Operation(
            summary = "Добавление заявки"
    )
    public ResponseEntity<String> addApplication(@RequestBody @Valid TrainingApplicationDTO applicationDTO,
                                                 BindingResult bindingResult,
                                                 @AuthenticationPrincipal User user){
        return trainingApplicationService.addApplication(applicationDTO, bindingResult, user);
    }

    @PutMapping("/{id}")
    @SecurityRequirement(name = "JWT")
    @PreAuthorize("hasAuthority('ADMIN')")
    @Operation(
            summary = "Одобрение заявки администратором"
    )
    public ResponseEntity<String> approveApplication(@PathVariable("id")
                                                   @Parameter(description = "Идентификатор заявки") long id){
        return trainingApplicationService.approveApplication(id);
    }

    @DeleteMapping("/{id}")
    @SecurityRequirement(name = "JWT")
    @PreAuthorize("hasAuthority('ADMIN')")
    @Operation(
            summary = "Удаление заявки"
    )
    public ResponseEntity<String> deleteApplication(@PathVariable("id")
                                                   @Parameter(description = "Идентификатор заявки") long id){
        return trainingApplicationService.deleteApplication(id);
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
