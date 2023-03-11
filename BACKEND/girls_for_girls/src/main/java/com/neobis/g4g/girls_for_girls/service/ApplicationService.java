package com.neobis.g4g.girls_for_girls.service;

import com.neobis.g4g.girls_for_girls.data.dto.ApplicationDTO;
import com.neobis.g4g.girls_for_girls.data.entity.Application;
import com.neobis.g4g.girls_for_girls.exception.NotAddedException;
import com.neobis.g4g.girls_for_girls.exception.NotUpdatedException;
import com.neobis.g4g.girls_for_girls.repository.ApplicationRepository;
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

@Service
public class ApplicationService {
    private final ApplicationRepository applicationRepository;

    @Autowired
    public ApplicationService(ApplicationRepository applicationRepository) {
        this.applicationRepository = applicationRepository;
    }


    public List<ApplicationDTO> getAllApplications() {
        return toApplicationDTO(applicationRepository.findAll());
    }


    public ResponseEntity<?> getApplicationById(long id) {
        if(applicationRepository.findById(id).isPresent()){
            return ResponseEntity.ok(toApplicationDTO(applicationRepository.findById(id).get()));
        }
        return new ResponseEntity<>("Application with id " + id + " wasn't found", HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<?> addApplication(ApplicationDTO applicationDTO,
                                            BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            throw new NotAddedException(getErrorMsg(bindingResult).toString());
        }

        if(applicationRepository.existsByEmail(applicationDTO.getEmail())){
            return ResponseEntity.badRequest().body("Application with this email already exists");
        }
        applicationRepository.save(toApplication(applicationDTO));
        return new ResponseEntity<>("Application was created", HttpStatus.CREATED);
    }

    public ResponseEntity<?> updateApplication(Long id, ApplicationDTO applicationDTO,
                                               BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            throw new NotUpdatedException(getErrorMsg(bindingResult).toString());
        }

        if(applicationRepository.findById(id).isPresent()){
            Application application = toApplication(applicationDTO);
            application.setId(id);
            applicationRepository.save(application);
            return ResponseEntity.ok("Application was updated");
        }else {
            return new ResponseEntity<>("Application with id " + id + " wasn't found", HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<?> deleteApplication(Long id){
        if(applicationRepository.existsById(id)){
            applicationRepository.deleteById(id);
            return ResponseEntity.ok("Application was deleted");
        }else{
            return new ResponseEntity<>("Application with id " + id + " wasn't found", HttpStatus.NOT_FOUND);
        }
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

    private Application toApplication(ApplicationDTO applicationDTO) {
        return Application.builder()
                .fullName(applicationDTO.getFullName())
                .email(applicationDTO.getEmail())
                .achievements(applicationDTO.getAchievements())
                .address(applicationDTO.getAddress())
                .aboutMe(applicationDTO.getAboutMe())
                .conferenceId(applicationDTO.getConferenceId())
                .dateOfBirth(applicationDTO.getDateOfBirth())
                .myFails(applicationDTO.getMyFails())
                .motivation(applicationDTO.getMotivation())
                .workFormat(applicationDTO.getWorkFormat())
                .mentorProgramId(applicationDTO.getMentorProgramId())
                .trainingId(applicationDTO.getTrainingId())
                .mySkills(applicationDTO.getMySkills())
                .recTime(Timestamp.valueOf(LocalDateTime.now()))
                .build();
    }
}
