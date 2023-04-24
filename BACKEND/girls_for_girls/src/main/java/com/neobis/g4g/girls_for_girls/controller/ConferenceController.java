package com.neobis.g4g.girls_for_girls.controller;

import com.neobis.g4g.girls_for_girls.data.dto.ConferencesDTO;
import com.neobis.g4g.girls_for_girls.exception.ErrorResponse;
import com.neobis.g4g.girls_for_girls.exception.NotAddedException;
import com.neobis.g4g.girls_for_girls.exception.NotUpdatedException;
import com.neobis.g4g.girls_for_girls.service.ConferenceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/conferences")
@Tag(
        name = "Контроллер для управления конференциями",
        description = "В этом контроллере вы можете добавлять, удалять, получать и обновлять данные конференций"
)
public class ConferenceController {
    private final ConferenceService conferenceService;


    @Autowired
    public ConferenceController(ConferenceService conferenceService) {
        this.conferenceService = conferenceService;
    }

    @GetMapping()
    @Operation(
            summary = "Получение всех конференций"
    )
    public List<ConferencesDTO> getAllConferences(){
        return conferenceService.getAllConferences();
    }

    @SecurityRequirement(name = "JWT")
    @GetMapping("/{id}/applications")
    @Operation(
            summary = "Получение заявок на конференцию по айди конференции"
    )
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> getAllApplicationsByConferenceId(@PathVariable("id")
                                                                 @Parameter(description = "Идентификатор конференции") long id){
        return conferenceService.getAllApplicationsByConferenceId(id);
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Получение конференции по айди"
    )
    public ResponseEntity<?> getConferenceById(@PathVariable("id")
                                               @Parameter(description = "Идентификатор конференции") long id){
        return conferenceService.getConferenceById(id);
    }

    @SecurityRequirement(name = "JWT")
    @PostMapping()
    @Operation(
            summary = "Добавление конференции"
    )
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> addConference(@RequestBody @Valid ConferencesDTO conferencesDTO,
                                           BindingResult bindingResult){
        return conferenceService.addConference(conferencesDTO, bindingResult);
    }

    @SecurityRequirement(name = "JWT")
    @PutMapping("/{id}")
    @Operation(
            summary = "Обновление данных конференции"
    )
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> updateConference(@PathVariable("id")
                                              @Parameter(description = "Идентификатор конференции") long id,
                                              @RequestBody @Valid ConferencesDTO conferencesDTO,
                                              BindingResult bindingResult){
        return conferenceService.updateConference(id, conferencesDTO, bindingResult);
    }

    @SecurityRequirement(name = "JWT")
    @DeleteMapping("/{id}")
    @Operation(
            summary = "Удаление конференции"
    )
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> deleteConference(@PathVariable("id")
                                              @Parameter(description = "Идентификатор конференции") long id){
        return conferenceService.deleteConference(id);
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
