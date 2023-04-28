package com.neobis.g4g.girls_for_girls.service;

import com.neobis.g4g.girls_for_girls.data.dto.ProductDTO;
import com.neobis.g4g.girls_for_girls.data.dto.SpeakerDTO;
import com.neobis.g4g.girls_for_girls.data.dto.VideoCourseCategoryDTO;
import com.neobis.g4g.girls_for_girls.data.entity.Speaker;
import com.neobis.g4g.girls_for_girls.data.entity.VideoCourseCategory;
import com.neobis.g4g.girls_for_girls.exception.NotAddedException;
import com.neobis.g4g.girls_for_girls.exception.NotUpdatedException;
import com.neobis.g4g.girls_for_girls.repository.SpeakerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.List;

import static com.neobis.g4g.girls_for_girls.data.dto.SpeakerDTO.toSpeakerDTO;

@Service
public class SpeakerService {
    private final SpeakerRepository speakerRepository;

    @Autowired
    public SpeakerService(SpeakerRepository speakerRepository) {
        this.speakerRepository = speakerRepository;
    }

    public List<SpeakerDTO> getAllSpeakers() {
        return toSpeakerDTO(speakerRepository.findAll());
    }

    public ResponseEntity<?> getSpeakerById(Long id) {
        if (speakerRepository.findById(id).isPresent()) {
            return ResponseEntity.ok(toSpeakerDTO(speakerRepository.findById(id).get()));
        }
        return new ResponseEntity<>("Speaker with this id: " + id + " not found", HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<Long> addSpeaker(SpeakerDTO speakerDTO,
                                                         BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            throw new NotAddedException(getErrorMsg(bindingResult).toString());
        }

        return ResponseEntity.ok(speakerRepository.save(
                Speaker.builder()
                        .full_info(speakerDTO.getFull_info())
                        .full_name(speakerDTO.getFull_name())
                        .facebook(speakerDTO.getFacebook())
                        .instagram(speakerDTO.getInstagram())
                        .whatsapp(speakerDTO.getWhatsapp())
                        .build()
        ).getId());
    }

    public ResponseEntity<String> updateSpeaker(Long id, SpeakerDTO speakerDTO,
                                                            BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            throw new NotUpdatedException(getErrorMsg(bindingResult).toString());
        }

        if(speakerRepository.findById(id).isPresent()){
            speakerRepository.save(
                    Speaker.builder()
                            .id(id)
                            .full_name(speakerDTO.getFull_name())
                            .full_info(speakerDTO.getFull_info())
                            .whatsapp(speakerDTO.getWhatsapp())
                            .instagram(speakerDTO.getInstagram())
                            .facebook(speakerDTO.getFacebook())
                            .build()
            );
            return new ResponseEntity<>("Speaker was updated", HttpStatus.OK);
        }else{
            return new ResponseEntity<>("Speaker with id " + id + " wasn't found", HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<String> deleteSpeakerById(Long id) {
        if(speakerRepository.existsById(id)){
            speakerRepository.deleteById(id);
            return ResponseEntity.ok("Speaker was deleted");
        }else{
            return new ResponseEntity<>("Speaker with id " + id + " wasn't found", HttpStatus.NOT_FOUND);
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

}
