package com.neobis.g4g.girls_for_girls.service;

import com.neobis.g4g.girls_for_girls.data.dto.ConferencesDTO;
import com.neobis.g4g.girls_for_girls.data.entity.Conference;
import com.neobis.g4g.girls_for_girls.exception.NotAddedException;
import com.neobis.g4g.girls_for_girls.exception.NotUpdatedException;
import com.neobis.g4g.girls_for_girls.repository.ApplicationRepository;
import com.neobis.g4g.girls_for_girls.repository.ConferencesRepository;
import com.neobis.g4g.girls_for_girls.repository.SpeakerRepository;
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
import static com.neobis.g4g.girls_for_girls.data.dto.ConferencesDTO.toConferencesDTO;
import static com.neobis.g4g.girls_for_girls.data.dto.FeedbackDTO.toFeedbackDTO;

@Service
public class ConferenceService {
    private final ConferencesRepository conferencesRepository;
    private final UserRepository userRepository;
    private final ApplicationRepository applicationRepository;
    private final SpeakerRepository speakerRepository;

    @Autowired
    public ConferenceService(ConferencesRepository conferencesRepository, UserRepository userRepository, ApplicationRepository applicationRepository, SpeakerRepository speakerRepository) {
        this.conferencesRepository = conferencesRepository;
        this.userRepository = userRepository;
        this.applicationRepository = applicationRepository;
        this.speakerRepository = speakerRepository;
    }

    public List<ConferencesDTO> getAllConferences(){
        return toConferencesDTO(conferencesRepository.findAll());
    }

    public ResponseEntity<?> getAllApplicationsByConferenceId(long id){
        if(conferencesRepository.existsById(id)){
            return ResponseEntity.ok(toApplicationDTO(applicationRepository.findAllByConferenceId(id)));
        }else {
            return new ResponseEntity<>("Conference with id " + id + " wasn't found", HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<?> getConferenceById(long id){
        if(conferencesRepository.findById(id).isPresent()){
            return ResponseEntity.ok(toConferencesDTO(conferencesRepository.findById(id).get()));
        }
        return new ResponseEntity<>("Conference with id " + id + " wasn't found", HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<?> addConference(ConferencesDTO conferencesDTO,
                                           BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            throw new NotAddedException(getErrorMsg(bindingResult).toString());
        }

        if(userRepository.existsById(conferencesDTO.getUserId())){
            if(speakerRepository.existsById(conferencesDTO.getSpeakerId())) {
                Conference conference = toConference(conferencesDTO);
                conference.setUserId(userRepository.findById(conferencesDTO.getUserId()).get());
                conference.setRecTime(Timestamp.valueOf(LocalDateTime.now()));
                conferencesRepository.save(conference);
                return ResponseEntity.ok("Conference was created");
            }else{
                return new ResponseEntity<>("Please write correctly speaker id", HttpStatus.BAD_REQUEST);
            }
        }else {
            return new ResponseEntity<>("Write user id correctly", HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<?> updateConference(long id, ConferencesDTO conferencesDTO,
                                              BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            throw new NotUpdatedException(getErrorMsg(bindingResult).toString());
        }

        if(conferencesRepository.findById(id).isPresent()){
            if(userRepository.existsById(conferencesDTO.getUserId())){
                if(speakerRepository.existsById(conferencesDTO.getSpeakerId())) {
                    Conference conference = toConference(conferencesDTO);
                    conference.setId(id);
                    conference.setRecTime(conferencesRepository.findById(id).get().getRecTime());
                    conference.setUserId(userRepository.findById(conferencesDTO.getUserId()).get());
                    conferencesRepository.save(conference);
                    return ResponseEntity.ok("Conference was updated");
                }else {
                    return new ResponseEntity<>("Please write correctly speaker id", HttpStatus.BAD_REQUEST);
                }
            }else {
                return new ResponseEntity<>("Write user id correctly", HttpStatus.BAD_REQUEST);
            }

        }else{
            return new ResponseEntity<>("Conference with id " + id + " wasn't found", HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<?> deleteConference(Long id){
        if(conferencesRepository.existsById(id)){
            conferencesRepository.deleteById(id);
            return ResponseEntity.ok("Conference was deleted");
        }else{
            return new ResponseEntity<>("Conference with id " + id + " wasn't found", HttpStatus.NOT_FOUND);
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

    private Conference toConference(ConferencesDTO conferencesDTO){
        return Conference.builder()
                .conferenceDate(conferencesDTO.getConferenceDate())
                .description(conferencesDTO.getDescription())
                .build();
    }
}
