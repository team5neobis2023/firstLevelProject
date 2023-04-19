package com.neobis.g4g.girls_for_girls.controller;

import com.neobis.g4g.girls_for_girls.data.dto.SpeakerDTO;
import com.neobis.g4g.girls_for_girls.exception.ErrorResponse;
import com.neobis.g4g.girls_for_girls.exception.NotAddedException;
import com.neobis.g4g.girls_for_girls.exception.NotUpdatedException;
import com.neobis.g4g.girls_for_girls.service.SpeakerService;
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
@RequestMapping("/api/v1/speakers")
@Tag(
        name = "Контроллер для управления спикерами",
        description = "В этом контроллере вы сможете добавлять, изменять, получать и удалять спикеров"
)
public class SpeakerController {
    private final SpeakerService speakerService;

    @Autowired
    public SpeakerController(SpeakerService speakerService) {
        this.speakerService = speakerService;
    }

    @GetMapping()
    @Operation(
            summary = "Получение всех спикеров",
            tags = "Спикер"
    )
    public List<SpeakerDTO> getAllSpeakers(){
        return speakerService.getAllSpeakers();
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Получение спикера по айди",
            tags = "Спикер"
    )
    public ResponseEntity<?> getSpeakerById(@PathVariable("id")
                                             @Parameter(description = "Идентификатор спикера") long id){
        return speakerService.getSpeakerById(id);
    }

    @SecurityRequirement(name = "JWT")
    @PostMapping()
    @Operation(
            summary = "Добавление спикера",
            tags = "Спикер"
    )
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> addSpeaker(@RequestBody @Valid SpeakerDTO speakerDTO,
                                         BindingResult bindingResult){
        return speakerService.addSpeaker(speakerDTO, bindingResult);
    }

    @SecurityRequirement(name = "JWT")
    @PutMapping("/{id}")
    @Operation(
            summary = "Обновление данных спикера",
            tags = "Спикер"
    )
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> updateSpeaker(@PathVariable("id")
                                            @Parameter(description = "Идентификатор спикера") long id,
                                            @RequestBody @Valid SpeakerDTO speakerDTO,
                                            BindingResult bindingResult){
        return speakerService.updateSpeaker(id, speakerDTO, bindingResult);
    }

    @SecurityRequirement(name = "JWT")
    @DeleteMapping("/{id}")
    @Operation(
            summary = "Удаление спикера",
            tags = "Спикер"
    )
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> deleteSpeaker(@PathVariable("id")
                                            @Parameter(description = "Идентификатор спикера") long id){
        return speakerService.deleteSpeakerById(id);
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
