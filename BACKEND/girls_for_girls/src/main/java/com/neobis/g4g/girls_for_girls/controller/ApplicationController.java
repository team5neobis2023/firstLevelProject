package com.neobis.g4g.girls_for_girls.controller;

import com.neobis.g4g.girls_for_girls.data.dto.ApplicationDTO;
import com.neobis.g4g.girls_for_girls.exception.ErrorResponse;
import com.neobis.g4g.girls_for_girls.exception.NotAddedException;
import com.neobis.g4g.girls_for_girls.exception.NotUpdatedException;
import com.neobis.g4g.girls_for_girls.service.ApplicationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/applications")
@Tag(
        name = "Контроллер для управления заявками",
        description = "В этом контроллере вы можете добавлять, удалять, получать, а также обновлять данные заявок"
)
public class ApplicationController {
    private final ApplicationService applicationService;

    @Autowired
    public ApplicationController(ApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    @GetMapping()
    @Operation(
            summary = "Получение всех заявок",
            tags = "Заявка"
    )
    public List<ApplicationDTO> getAllApplications(){
        return applicationService.getAllApplications();
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Получение заявки",
            description = "Получение заявки по конкретному айди",
            tags = "Заявка"
    )
    public ResponseEntity<?> getApplicationById(@PathVariable("id")
                                                 @Parameter(description = "Идентификатор заявки")
                                                 long id){
        return applicationService.getApplicationById(id);
    }

    @PostMapping()
    @Operation(
            summary = "Добавление заявки",
            tags = "Заявка"
    )
    public ResponseEntity<?> addApplication(@RequestBody @Valid ApplicationDTO applicationDTO,
                                            BindingResult bindingResult){
        return applicationService.addApplication(applicationDTO, bindingResult);
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Обновление данных заявки",
            tags = "Заявка"
    )
    public ResponseEntity<?> updateApplication(@PathVariable("id")
                                                   @Parameter(description = "Идентификатор заявки") long id,
                                               @RequestBody @Valid ApplicationDTO applicationDTO,
                                               BindingResult bindingResult){
        return applicationService.updateApplication(id, applicationDTO, bindingResult);
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Удаление заявки",
            tags = "Заявка"
    )
    public ResponseEntity<?> deleteApplication(@PathVariable("id")
                                                   @Parameter(description = "Идентификатор заявки") long id){
        return applicationService.deleteApplication(id);
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
