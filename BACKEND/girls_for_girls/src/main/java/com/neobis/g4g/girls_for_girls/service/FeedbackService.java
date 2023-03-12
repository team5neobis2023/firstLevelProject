package com.neobis.g4g.girls_for_girls.service;

import com.neobis.g4g.girls_for_girls.data.dto.FeedbackDTO;
import com.neobis.g4g.girls_for_girls.data.entity.Conference;
import com.neobis.g4g.girls_for_girls.data.entity.Feedback;
import com.neobis.g4g.girls_for_girls.exception.ErrorResponse;
import com.neobis.g4g.girls_for_girls.exception.NotAddedException;
import com.neobis.g4g.girls_for_girls.exception.NotUpdatedException;
import com.neobis.g4g.girls_for_girls.repository.FeedbackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

import static com.neobis.g4g.girls_for_girls.data.dto.ConferencesDTO.toConferencesDTO;
import static com.neobis.g4g.girls_for_girls.data.dto.FeedbackDTO.toFeedbackDTO;

@Service
public class FeedbackService {
    private final FeedbackRepository feedbackRepository;

    @Autowired
    public FeedbackService(FeedbackRepository feedbackRepository) {
        this.feedbackRepository = feedbackRepository;
    }

    public List<FeedbackDTO> getAllFeedbacks(){
        return toFeedbackDTO(feedbackRepository.findAll());
    }

    public ResponseEntity<?> getFeedbackById(long id){
        if(feedbackRepository.findById(id).isPresent()){
            return ResponseEntity.ok(toFeedbackDTO(feedbackRepository.findById(id).get()));
        }
        return new ResponseEntity<>("Feedback with id " + id + " wasn't found", HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<?> addFeedback(FeedbackDTO feedbackDTO,
                                         BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            throw new NotAddedException(getErrorMsg(bindingResult).toString());
        }

        feedbackRepository.save(toFeedback(feedbackDTO));
        return new ResponseEntity<>("Feedback was added", HttpStatus.CREATED);
    }

    public ResponseEntity<?> updateFeedback(long id, FeedbackDTO feedbackDTO,
                                            BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            throw new NotUpdatedException(getErrorMsg(bindingResult).toString());
        }

        if(feedbackRepository.findById(id).isPresent()){
            Feedback feedback = toFeedback(feedbackDTO);
            feedback.setId(id);
            feedbackRepository.save(feedback);
            return ResponseEntity.ok("Feedback was updated");
        }else{
            return new ResponseEntity<>("Feedback with id " + id + " wasn't found", HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<?> deleteFeedback(long id){
        if(feedbackRepository.existsById(id)){
            feedbackRepository.deleteById(id);
            return ResponseEntity.ok("Feedback was deleted");
        }else{
            return new ResponseEntity<>("Feedback with id " + id + " wasn't found", HttpStatus.NOT_FOUND);
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

    public static Feedback toFeedback(FeedbackDTO feedbackDTO) {
        return Feedback.builder()
                .email(feedbackDTO.getEmail())
                .fullName(feedbackDTO.getFullName())
                .phoneNumber(feedbackDTO.getPhoneNumber())
                .videoCourse(feedbackDTO.getVideoCourse())
                .message(feedbackDTO.getMessage())
                .recTime(Timestamp.valueOf(LocalDateTime.now()))
                .build();
    }

}