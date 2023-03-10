package com.neobis.g4g.girls_for_girls.controller;

import com.neobis.g4g.girls_for_girls.data.dto.ConferencesDTO;
import com.neobis.g4g.girls_for_girls.service.ConferencesService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/conferences")
@Tag(
        name = "Контроллер для управления конференциями",
        description = "В этом контроллере вы можете добавлять, удалять, получать и обновлять данные конференций"
)
public class ConferenceController {
    private final ConferencesService conferencesService;


    @Autowired
    public ConferenceController(ConferencesService conferencesService) {
        this.conferencesService = conferencesService;
    }

    @GetMapping()
    @Operation(
            summary = "Получение всех конференций",
            tags = "Конференция"
    )
    public List<ConferencesDTO> getAllConferences(){
        return conferencesService.getAllConferences();
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Получение конференции по айди",
            tags = "Конференция"
    )
    public ResponseEntity<?> getConferenceById(@PathVariable("id")
                                               @Parameter(description = "Идентификатор конференции") long id){
        return conferencesService.getConferenceById(id);
    }

    @PostMapping()
    @Operation(
            summary = "Добавление конференции",
            tags = "Конференция"
    )
    public ResponseEntity<?> addConference(@RequestBody ConferencesDTO conferencesDTO){
        return conferencesService.addConference(conferencesDTO);
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Обновление данных конференции",
            tags = "Конференция"
    )
    public ResponseEntity<?> updateConference(@PathVariable("id")
                                              @Parameter(description = "Идентификатор конференции") long id,
                                              @RequestBody ConferencesDTO conferencesDTO){
        return conferencesService.updateConference(id, conferencesDTO);
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Удаление конференции",
            tags = "Конференция"
    )
    public ResponseEntity<?> deleteConference(@PathVariable("id")
                                              @Parameter(description = "Идентификатор конференции") long id){
        return conferencesService.deleteConference(id);
    }
}
