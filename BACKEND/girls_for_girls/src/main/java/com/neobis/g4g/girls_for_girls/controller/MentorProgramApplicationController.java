package com.neobis.g4g.girls_for_girls.controller;

import com.neobis.g4g.girls_for_girls.data.dto.MentorProgramApplicationDTO;
import com.neobis.g4g.girls_for_girls.data.dto.TrainingApplicationDTO;
import com.neobis.g4g.girls_for_girls.data.entity.MentorProgramApplication;
import com.neobis.g4g.girls_for_girls.data.entity.TrainingApplication;
import com.neobis.g4g.girls_for_girls.data.entity.User;
import com.neobis.g4g.girls_for_girls.service.MentorProgramApplicationService;
import com.neobis.g4g.girls_for_girls.service.TrainingApplicationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/mentorProgram-application")
@Tag(
        name = "Контроллер для управления заявками на менторские программы",
        description = "В этом контроллере вы можете добавлять, удалять и получать данные заявок на менторские программы"
)
@AllArgsConstructor
public class MentorProgramApplicationController {
    private final MentorProgramApplicationService mentorProgramApplicationService;

    @SecurityRequirement(name = "JWT")
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping()
    @Operation(
            summary = "Получение всех заявок"
    )
    public Page<MentorProgramApplication> getAllApplications(@PageableDefault Pageable pageable){
        return mentorProgramApplicationService.getAllApplications(pageable);
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
        return mentorProgramApplicationService.getApplicationById(id);
    }

    @SecurityRequirement(name = "JWT")
    @PostMapping()
    @Operation(
            summary = "Добавление заявки"
    )
    public ResponseEntity<?> addApplication(@RequestBody @Valid MentorProgramApplicationDTO applicationDTO,
                                                 @AuthenticationPrincipal User user){
        return mentorProgramApplicationService.addApplication(applicationDTO, user);
    }

    @PutMapping("/{id}")
    @SecurityRequirement(name = "JWT")
    @PreAuthorize("hasAuthority('ADMIN')")
    @Operation(
            summary = "Одобрение заявки администратором"
    )
    public ResponseEntity<String> approveApplication(@PathVariable("id")
                                                     @Parameter(description = "Идентификатор заявки") long id){
        return mentorProgramApplicationService.approveApplication(id);
    }

    @DeleteMapping("/{id}")
    @SecurityRequirement(name = "JWT")
    @PreAuthorize("hasAuthority('ADMIN')")
    @Operation(
            summary = "Удаление заявки"
    )
    public ResponseEntity<String> deleteApplication(@PathVariable("id")
                                                    @Parameter(description = "Идентификатор заявки") long id){
        return mentorProgramApplicationService.deleteApplication(id);
    }
}
