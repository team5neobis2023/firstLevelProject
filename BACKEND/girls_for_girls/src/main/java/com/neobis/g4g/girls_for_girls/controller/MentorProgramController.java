package com.neobis.g4g.girls_for_girls.controller;

import com.neobis.g4g.girls_for_girls.data.dto.ConferencesDTO;
import com.neobis.g4g.girls_for_girls.data.dto.MentorProgramDTO;
import com.neobis.g4g.girls_for_girls.data.entity.MentorProgram;
import com.neobis.g4g.girls_for_girls.exception.ErrorResponse;
import com.neobis.g4g.girls_for_girls.exception.NotAddedException;
import com.neobis.g4g.girls_for_girls.exception.NotUpdatedException;
import com.neobis.g4g.girls_for_girls.service.MentorProgramService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/mentorPrograms")
@Tag(
        name = "Контроллер для управления менторскими программами",
        description = "В этом контроллере вы можете добавлять, удалять, получать и обновлять данные менторских программ"
)
public class MentorProgramController {
    private final MentorProgramService mentorProgramService;

    @Autowired
    public MentorProgramController(MentorProgramService mentorProgramService) {
        this.mentorProgramService = mentorProgramService;
    }

    @GetMapping()
    @Operation(
            summary = "Получение всех менторских программ"
    )
    public Page<MentorProgram> getAllMentorPrograms(@PageableDefault Pageable pageable){
        return mentorProgramService.getAllMentorPrograms(pageable);
    }

    @SecurityRequirement(name = "JWT")
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/{id}/applications")
    @Operation(
            summary = "Получение заявок на менторскую программу по айди программы"
    )
    public ResponseEntity<?> getAllApplicationsByMentorProgramId(@PathVariable("id")
                                                                      @Parameter(description = "Идентификатор менторской программы") long id){
        return mentorProgramService.getAllApplicationsByMentorProgramId(id);
    }

    @SecurityRequirement(name = "JWT")
    @GetMapping("/{id}")
    @Operation(
            summary = "Получение менторской программы по айди"
    )
    public ResponseEntity<?> getMentorProgramById(@PathVariable("id")
                                               @Parameter(description = "Идентификатор менторской программы") long id){
        return mentorProgramService.getMentorProgramById(id);
    }

    @SecurityRequirement(name = "JWT")
    @PostMapping()
    @Operation(
            summary = "Добавление менторской программы"
    )
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> addMentorProgram(@RequestBody @Valid MentorProgramDTO mentorProgramDTO,
                                           BindingResult bindingResult){
        return mentorProgramService.addMentorProgram(mentorProgramDTO, bindingResult);
    }

    @SecurityRequirement(name = "JWT")
    @PutMapping("/{id}")
    @Operation(
            summary = "Обновление данных менторской программы"
    )
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> updateMentorProgram(@PathVariable("id")
                                              @Parameter(description = "Идентификатор менторской программы") long id,
                                              @RequestBody @Valid MentorProgramDTO mentorProgramDTO,
                                              BindingResult bindingResult){
        return mentorProgramService.updateMentorProgram(id, mentorProgramDTO, bindingResult);
    }

    @SecurityRequirement(name = "JWT")
    @DeleteMapping("/{id}")
    @Operation(
            summary = "Удаление менторской программы"
        )
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> deleteMentorProgram(@PathVariable("id")
                                              @Parameter(description = "Идентификатор менторской программы") long id){
        return mentorProgramService.deleteMentorProgram(id);
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
