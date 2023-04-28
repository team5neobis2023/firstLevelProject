package com.neobis.g4g.girls_for_girls.service;

import com.neobis.g4g.girls_for_girls.data.dto.FeedbackDTO;
import com.neobis.g4g.girls_for_girls.data.entity.Feedback;
import com.neobis.g4g.girls_for_girls.data.entity.User;
import com.neobis.g4g.girls_for_girls.exception.NotAddedException;
import com.neobis.g4g.girls_for_girls.exception.NotUpdatedException;
import com.neobis.g4g.girls_for_girls.repository.FeedbackRepository;
import com.neobis.g4g.girls_for_girls.repository.UserRepository;
import com.neobis.g4g.girls_for_girls.repository.VideoCourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

import static com.neobis.g4g.girls_for_girls.data.dto.ConferencesDTO.toConferencesDTO;
import static com.neobis.g4g.girls_for_girls.data.dto.FeedbackDTO.toFeedbackDTO;

@Service
public class FeedbackService {
    private final FeedbackRepository feedbackRepository;
    private final VideoCourseRepository videoCourseRepository;
    private final UserRepository userRepository;

    @Autowired
    public FeedbackService(FeedbackRepository feedbackRepository, VideoCourseRepository videoCourseRepository, UserRepository userRepository) {
        this.feedbackRepository = feedbackRepository;
        this.videoCourseRepository = videoCourseRepository;
        this.userRepository = userRepository;
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
                                         BindingResult bindingResult,
                                         User user){
        if(bindingResult.hasErrors()){
            throw new NotAddedException(getErrorMsg(bindingResult).toString());
        }

        if(videoCourseRepository.existsById(feedbackDTO.getVideoCourseId())){
            Feedback feedback = toFeedback(feedbackDTO);
            feedback.setVideoCourse(videoCourseRepository.findById(feedbackDTO.getVideoCourseId()).get());
            feedback.setUser(user);
            feedback.setRecTime(Timestamp.valueOf(LocalDateTime.now()));
            feedbackRepository.save(feedback);
            return ResponseEntity.ok("Feedback was added");
        }else{
            return new ResponseEntity<>("Write video course id correctly", HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<?> updateFeedback(long id, FeedbackDTO feedbackDTO,
                                            BindingResult bindingResult,
                                            User user){
        if(bindingResult.hasErrors()){
            throw new NotUpdatedException(getErrorMsg(bindingResult).toString());
        }

        if(feedbackRepository.findById(id).isPresent()){
            if(videoCourseRepository.existsById(feedbackDTO.getVideoCourseId())){
                    Feedback feedback = toFeedback(feedbackDTO);
                    feedback.setId(id);
                    feedback.setVideoCourse(videoCourseRepository.findById(feedbackDTO.getVideoCourseId()).get());
                    feedback.setUser(user);
                    feedback.setRecTime(feedbackRepository.findById(id).get().getRecTime());
                    feedbackRepository.save(feedback);
                    return ResponseEntity.ok("Feedback was updated");
            }else{
                return new ResponseEntity<>("Write video course id correctly", HttpStatus.BAD_REQUEST);
            }
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
                .message(feedbackDTO.getMessage())
                .build();
    }

}
