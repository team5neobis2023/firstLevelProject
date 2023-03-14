package com.neobis.g4g.girls_for_girls.controller;

import com.neobis.g4g.girls_for_girls.data.dto.MentorProgramDTO;
import com.neobis.g4g.girls_for_girls.data.dto.TrainingDTO;
import com.neobis.g4g.girls_for_girls.exception.ErrorResponse;
import com.neobis.g4g.girls_for_girls.exception.NotAddedException;
import com.neobis.g4g.girls_for_girls.exception.NotUpdatedException;
import com.neobis.g4g.girls_for_girls.service.TrainingService;
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
@RequestMapping("/api/v1/trainings")
@Tag(
        name = "Контроллер для управления тренингами",
        description = "В этом контроллере вы можете добавлять, удалять, получать и обновлять данные тренингов"
)
public class TrainingController {
    private final TrainingService trainingService;

    @Autowired
    public TrainingController(TrainingService trainingService) {
        this.trainingService = trainingService;
    }

    @GetMapping()
    @Operation(
            summary = "Получение всех тренингов",
            tags = "Тренинг"
    )
    public List<TrainingDTO> getAllMentorPrograms(){
        return trainingService.getAllTrainings();
    }

    @GetMapping("/{id}/applications")
    @Operation(
            summary = "Получение заявок на тренинг по айди тренинга",
            tags = "Тренинг"
    )
    public ResponseEntity<?> getAllApplicationsByMentorProgramId(@PathVariable("id")
                                                                 @Parameter(description = "Идентификатор тренинга") long id){
        return trainingService.getAllApplicationsByTrainingId(id);
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Получение тренинга по айди",
            tags = "Тренинг"
    )
    public ResponseEntity<?> getMentorProgramById(@PathVariable("id")
                                                  @Parameter(description = "Идентификатор тренинга") long id){
        return trainingService.getTrainingById(id);
    }

    @PostMapping()
    @Operation(
            summary = "Добавление тренинга",
            tags = "Тренинг"
    )
    public ResponseEntity<?> addMentorProgram(@RequestBody @Valid TrainingDTO trainingDTO,
                                              BindingResult bindingResult){
        return trainingService.addTraining(trainingDTO, bindingResult);
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Обновление данных тренинга",
            tags = "Тренинг"
    )
    public ResponseEntity<?> updateMentorProgram(@PathVariable("id")
                                                 @Parameter(description = "Идентификатор тренинга") long id,
                                                 @RequestBody @Valid TrainingDTO trainingDTO,
                                                 BindingResult bindingResult){
        return trainingService.updateTraining(id, trainingDTO, bindingResult);
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Удаление тренинга",
            tags = "Тренинг"
    )
    public ResponseEntity<?> deleteConference(@PathVariable("id")
                                              @Parameter(description = "Идентификатор тренинга") long id){
        return trainingService.deleteTraining(id);
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
