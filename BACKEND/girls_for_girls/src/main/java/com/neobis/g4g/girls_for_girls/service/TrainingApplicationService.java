package com.neobis.g4g.girls_for_girls.service;

import com.neobis.g4g.girls_for_girls.data.dto.NotificationDTO;
import com.neobis.g4g.girls_for_girls.data.dto.TrainingApplicationDTO;
import com.neobis.g4g.girls_for_girls.data.entity.Notification;
import com.neobis.g4g.girls_for_girls.data.entity.Training;
import com.neobis.g4g.girls_for_girls.data.entity.TrainingApplication;
import com.neobis.g4g.girls_for_girls.data.entity.User;
import com.neobis.g4g.girls_for_girls.exception.NotAddedException;
import com.neobis.g4g.girls_for_girls.exception.NotUpdatedException;
import com.neobis.g4g.girls_for_girls.repository.*;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;


@Service
@AllArgsConstructor
public class TrainingApplicationService {
    private final TrainingApplicationRepository trainingApplicationRepository;
    private final TrainingRepository trainingRepository;
    private final NotificationRepo notificationRepo;

    public Page<TrainingApplication> getAllApplications(Pageable pageable) {
        List<TrainingApplication> trainingApplications = trainingApplicationRepository.findAll();
        return new PageImpl<>(trainingApplications, pageable, trainingApplications.size());
    }


    public ResponseEntity<?> getApplicationById(long id) {
        if(trainingApplicationRepository.findById(id).isPresent()){
            return ResponseEntity.ok(trainingApplicationRepository.findById(id).get());
        }
        return new ResponseEntity<>("Application with id " + id + " wasn't found", HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<String> addApplication(TrainingApplicationDTO applicationDTO,
                                            BindingResult bindingResult,
                                                 User user) {
        if(bindingResult.hasErrors()){
            throw new NotAddedException(getErrorMsg(bindingResult).toString());
        }

        if(trainingApplicationRepository.existsByEmailAndTrainingId(applicationDTO.getEmail(), applicationDTO.getTraining_id())){
            return ResponseEntity.badRequest().body("Application with this email already exists");
        }

        TrainingApplication trainingApplication = toApplication(applicationDTO);
        trainingApplication.setUser(user);
        trainingApplicationRepository.save(trainingApplication);
        return ResponseEntity.ok("Application was created");
    }

    public ResponseEntity<String> approveApplication(Long id){

        if(trainingApplicationRepository.findById(id).isEmpty()) {
            return new ResponseEntity<>("Application with id " + id + " wasn't found", HttpStatus.NOT_FOUND);
        }

        TrainingApplication trainingApplication = trainingApplicationRepository.findById(id).get();
        trainingApplication.setApproved(true);
        trainingApplicationRepository.save(trainingApplication);

        Training training = trainingApplication.getTraining();

        Notification notification = new Notification();
        notification.setHeader("Одобрение заявки на тренинг");
        notification.setMessage("Поздравляем! Ваша заявка на тренинг " + training.getHeader() + " одобрена.");
        notification.setUser(trainingApplication.getUser());
        notification.setRecTime(Timestamp.valueOf(LocalDateTime.now()));
        notificationRepo.save(notification);

        return new ResponseEntity<>("Application was approved", HttpStatus.OK);
    }

    public ResponseEntity<String> deleteApplication(Long id){
        if(trainingApplicationRepository.existsById(id)){
            trainingApplicationRepository.deleteById(id);
            return ResponseEntity.ok("Application was deleted");
        }else{
            return new ResponseEntity<>("Application with id " + id + " wasn't found", HttpStatus.NOT_FOUND);
        }
    }

    private TrainingApplication toApplication(TrainingApplicationDTO applicationDTO) {
        return TrainingApplication.builder()
                .fullName(applicationDTO.getFullName())
                .dateOfBirth(applicationDTO.getDateOfBirth())
                .email(applicationDTO.getEmail())
                .phoneNumber(applicationDTO.getPhoneNumber())
                .address(applicationDTO.getAddress())
                .opportunityToCome(applicationDTO.getOpportunityToCome())
                .reasonToParticipate(applicationDTO.getReasonToParticipate())
                .expectation(applicationDTO.getExpectation())
                .mostInterestTrainingTopic(applicationDTO.getMostInterestTrainingTopic())
                .otherInterestTopics(applicationDTO.getOtherInterestTopics())
                .whereFoundTraining(applicationDTO.getWhereFoundTraining())
                .training(trainingRepository.findById(applicationDTO.getTraining_id()).get())
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
