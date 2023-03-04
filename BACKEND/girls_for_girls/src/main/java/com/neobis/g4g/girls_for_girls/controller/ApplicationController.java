package com.neobis.g4g.girls_for_girls.controller;

import com.neobis.g4g.girls_for_girls.data.dto.ApplicationDTO;
import com.neobis.g4g.girls_for_girls.service.ApplicationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    //TODO
//    @PostMapping()
//    @Operation(
//            summary = "Добавление заявки",
//            tags = "Заявка"
//    )
//    public ResponseEntity<?> addApplication(@RequestBody ApplicationDTO applicationDTO){
//        return applicationService.addApplication(applicationDTO);
//    }
}
