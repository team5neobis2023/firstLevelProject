package com.neobis.g4g.girls_for_girls.service;

import com.neobis.g4g.girls_for_girls.data.dto.ApplicationDTO;
import com.neobis.g4g.girls_for_girls.data.entity.Application;
import com.neobis.g4g.girls_for_girls.exception.NotAddedException;
import com.neobis.g4g.girls_for_girls.exception.NotUpdatedException;
import com.neobis.g4g.girls_for_girls.repository.ApplicationRepository;
import com.neobis.g4g.girls_for_girls.repository.ConferencesRepository;
import com.neobis.g4g.girls_for_girls.repository.MentorProgramRepository;
import com.neobis.g4g.girls_for_girls.repository.TrainingRepository;
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
    private final ConferencesRepository conferencesRepository;
    private final MentorProgramRepository mentorProgramRepository;
    private final TrainingRepository trainingRepository;

    @Autowired
    public ApplicationService(ApplicationRepository applicationRepository, ConferencesRepository conferencesRepository, MentorProgramRepository mentorProgramRepository, TrainingRepository trainingRepository) {
        this.applicationRepository = applicationRepository;
        this.conferencesRepository = conferencesRepository;
        this.mentorProgramRepository = mentorProgramRepository;
        this.trainingRepository = trainingRepository;
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
        if(conferenceExistsById(applicationDTO) && mentorProgramExistsById(applicationDTO) && trainingExistsById(applicationDTO)){

            Application application = toApplication(applicationDTO);
            application.setRecTime(Timestamp.valueOf(LocalDateTime.now()));
            application.setTraining(trainingRepository.findById(applicationDTO.getTrainingId()).get());
            application.setConference(conferencesRepository.findById(applicationDTO.getConferenceId()).get());
            application.setMentorProgram(mentorProgramRepository.findById(applicationDTO.getMentorProgramId()).get());
            applicationRepository.save(application);
            return new ResponseEntity<>("Application was created", HttpStatus.CREATED);

        }else{
            return new ResponseEntity<>("Please write correctly conference id/mentor program id/training id", HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<?> updateApplication(Long id, ApplicationDTO applicationDTO,
                                               BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            throw new NotUpdatedException(getErrorMsg(bindingResult).toString());
        }

        if(applicationRepository.findById(id).isPresent()){
            if(conferenceExistsById(applicationDTO)
                    && mentorProgramExistsById(applicationDTO)
                    && trainingExistsById(applicationDTO)){

                Application application = toApplication(applicationDTO);
                application.setId(id);
                application.setRecTime(applicationRepository.findById(id).get().getRecTime());
                application.setTraining(trainingRepository.findById(applicationDTO.getTrainingId()).get());
                application.setConference(conferencesRepository.findById(applicationDTO.getConferenceId()).get());
                application.setMentorProgram(mentorProgramRepository.findById(applicationDTO.getMentorProgramId()).get());
                applicationRepository.save(application);
                return new ResponseEntity<>("Application was updated", HttpStatus.OK);

            }else{
                return new ResponseEntity<>("Please write correctly conference id/mentor program id/training id", HttpStatus.BAD_REQUEST);
            }

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

    private boolean conferenceExistsById(ApplicationDTO applicationDTO){
        return conferencesRepository.existsById(applicationDTO.getConferenceId());
    }

    private boolean mentorProgramExistsById(ApplicationDTO applicationDTO){
        return mentorProgramRepository.existsById(applicationDTO.getMentorProgramId());
    }

    private boolean trainingExistsById(ApplicationDTO applicationDTO){
        return trainingRepository.existsById(applicationDTO.getTrainingId());
    }

    private Application toApplication(ApplicationDTO applicationDTO) {
        return Application.builder()
                .fullName(applicationDTO.getFullName())
                .email(applicationDTO.getEmail())
                .achievements(applicationDTO.getAchievements())
                .address(applicationDTO.getAddress())
                .aboutMe(applicationDTO.getAboutMe())
                .dateOfBirth(applicationDTO.getDateOfBirth())
                .myFails(applicationDTO.getMyFails())
                .motivation(applicationDTO.getMotivation())
                .workFormat(applicationDTO.getWorkFormat())
                .mySkills(applicationDTO.getMySkills())
                .build();
    }
}
