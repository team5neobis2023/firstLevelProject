package com.neobis.g4g.girls_for_girls.service;

import com.neobis.g4g.girls_for_girls.data.dto.TrainingDTO;
import com.neobis.g4g.girls_for_girls.data.entity.Training;
import com.neobis.g4g.girls_for_girls.exception.NotAddedException;
import com.neobis.g4g.girls_for_girls.exception.NotUpdatedException;
import com.neobis.g4g.girls_for_girls.repository.ApplicationRepository;
import com.neobis.g4g.girls_for_girls.repository.SpeakerRepository;
import com.neobis.g4g.girls_for_girls.repository.TrainingRepository;
import com.neobis.g4g.girls_for_girls.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

import static com.neobis.g4g.girls_for_girls.data.dto.ApplicationDTO.toApplicationDTO;
import static com.neobis.g4g.girls_for_girls.data.dto.TrainingDTO.toTrainingDTO;

@Service
public class TrainingService {
    private final TrainingRepository trainingRepository;
    private final UserRepository userRepository;
    private final ApplicationRepository applicationRepository;
    private final SpeakerRepository speakerRepository;

    @Autowired
    public TrainingService(TrainingRepository trainingRepository, UserRepository userRepository, ApplicationRepository applicationRepository, SpeakerRepository speakerRepository) {
        this.trainingRepository = trainingRepository;
        this.userRepository = userRepository;
        this.applicationRepository = applicationRepository;
        this.speakerRepository = speakerRepository;
    }

    public List<TrainingDTO> getAllTrainings(){
        return toTrainingDTO(trainingRepository.findAll());
    }

    public ResponseEntity<?> getTrainingById(long id){
        if(trainingRepository.findById(id).isPresent()){
            return ResponseEntity.ok(toTrainingDTO(trainingRepository.findById(id).get()));
        }
        return new ResponseEntity<>("Training with id " + id + " wasn't found", HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<?> getAllApplicationsByTrainingId(long id){
        if(trainingRepository.existsById(id)){
            return ResponseEntity.ok(toApplicationDTO(applicationRepository.findAllByTrainingId(id)));
        }else {
            return new ResponseEntity<>("Training with id " + id + " wasn't found", HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<?> addTraining(TrainingDTO trainingDTO,
                                            BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            throw new NotAddedException(getErrorMsg(bindingResult).toString());
        }

        if(userRepository.existsById(trainingDTO.getUserId())){
            if(speakerRepository.existsById(trainingDTO.getSpeakerId())) {

                Training training = toTraining(trainingDTO);
                training.setUser(userRepository.findById(trainingDTO.getUserId()).get());
                training.setRecTime(Timestamp.valueOf(LocalDateTime.now()));
                trainingRepository.save(training);
                return ResponseEntity.ok("Training was created");

            }else{
                return new ResponseEntity<>("Please write correctly speaker id", HttpStatus.BAD_REQUEST);
            }
        }else{
            return new ResponseEntity<>("Please write correctly user id", HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<?> updateTraining(Long id, TrainingDTO trainingDTO,
                                               BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            throw new NotUpdatedException(getErrorMsg(bindingResult).toString());
        }

        if(trainingRepository.findById(id).isPresent()){
            if(userRepository.existsById(trainingDTO.getUserId())){
                if(speakerRepository.existsById(trainingDTO.getSpeakerId())) {
                    Training training = toTraining(trainingDTO);
                    training.setId(id);
                    training.setUser(userRepository.findById(trainingDTO.getUserId()).get());
                    training.setRecTime(trainingRepository.findById(id).get().getRecTime());
                    trainingRepository.save(training);
                    return new ResponseEntity<>("Training was updated", HttpStatus.OK);
                }else{
                    return new ResponseEntity<>("Please write correctly speaker id", HttpStatus.BAD_REQUEST);
                }
            }else{
                return new ResponseEntity<>("Please write correctly user id", HttpStatus.BAD_REQUEST);
            }

        }else {
            return new ResponseEntity<>("Training with id " + id + " wasn't found", HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<?> deleteTraining(Long id){
        if(trainingRepository.existsById(id)){
            trainingRepository.deleteById(id);
            return ResponseEntity.ok("Training was deleted");
        }else{
            return new ResponseEntity<>("Training with id " + id + " wasn't found", HttpStatus.NOT_FOUND);
        }
    }

    private Training toTraining(TrainingDTO trainingDTO) {
        return Training.builder()
                .description(trainingDTO.getDescription())
                .build();
    }

    private StringBuilder getErrorMsg(BindingResult bindingResult){
        StringBuilder errorMsg = new StringBuilder();
        List<FieldError> errors = bindingResult.getFieldErrors();
        for (FieldError error : errors) {
            errorMsg.append(error.getField())
                    .append(" - ")
                    .append(error.getDefaultMessage())
                    .append("; ");
        }
        return errorMsg;
    }

}
