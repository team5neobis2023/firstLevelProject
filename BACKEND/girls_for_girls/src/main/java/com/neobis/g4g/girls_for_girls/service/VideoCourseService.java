package com.neobis.g4g.girls_for_girls.service;

import com.neobis.g4g.girls_for_girls.data.dto.ApplicationDTO;
import com.neobis.g4g.girls_for_girls.data.dto.FeedbackDTO;
import com.neobis.g4g.girls_for_girls.data.dto.VideoCourseDTO;
import com.neobis.g4g.girls_for_girls.data.entity.Application;
import com.neobis.g4g.girls_for_girls.data.entity.VideoCourse;
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

import static com.neobis.g4g.girls_for_girls.data.dto.FeedbackDTO.toFeedbackDTO;
import static com.neobis.g4g.girls_for_girls.data.dto.VideoCourseDTO.toVideoCourseDTO;

@Service
public class VideoCourseService {
    private final VideoCourseRepository videoCourseRepository;
    private final UserRepository userRepository;
    private final FeedbackRepository feedbackRepository;

    @Autowired
    public VideoCourseService(VideoCourseRepository videoCourseRepository, UserRepository userRepository, FeedbackRepository feedbackRepository) {
        this.videoCourseRepository = videoCourseRepository;
        this.userRepository = userRepository;
        this.feedbackRepository = feedbackRepository;
    }

    public List<VideoCourseDTO> getAllVideoCourses(){
        return toVideoCourseDTO(videoCourseRepository.findAll());
    }

    public ResponseEntity<?> getAllFeedbacksByVideoCourseId(long id){
        if(videoCourseRepository.existsById(id)) {
            return ResponseEntity.ok(toFeedbackDTO(feedbackRepository.findAllByVideoCourseId(id)));
        }else {
            return new ResponseEntity<>("VideoCourse with id " + id + " wasn't found", HttpStatus.BAD_REQUEST);
        }
    }
    public ResponseEntity<?> getVideoCourseById(long id){
        if(videoCourseRepository.findById(id).isPresent()){
            return ResponseEntity.ok(toVideoCourseDTO(videoCourseRepository.findById(id).get()));
        }
        return new ResponseEntity<>("Video course with id " + id + " wasn't found", HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<String> addVideoCourse(VideoCourseDTO videoCourseDTO,
                                            BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            throw new NotAddedException(getErrorMsg(bindingResult).toString());
        }

        if(userRepository.existsById(videoCourseDTO.getUserId())){

            VideoCourse videoCourse = toVideoCourse(videoCourseDTO);
            videoCourse.setUserId(userRepository.findById(videoCourseDTO.getUserId()).get());
            videoCourse.setRecTime(Timestamp.valueOf(LocalDateTime.now()));
            videoCourseRepository.save(videoCourse);
            return new ResponseEntity<>("VideoCourse was created", HttpStatus.CREATED);

        }else{
            return new ResponseEntity<>("Please write correctly user id", HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<?> updateVideoCourse(Long id, VideoCourseDTO videoCourseDTO,
                                               BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            throw new NotUpdatedException(getErrorMsg(bindingResult).toString());
        }

        if(videoCourseRepository.findById(id).isPresent()){
            if(userRepository.existsById(videoCourseDTO.getUserId())){
                VideoCourse videoCourse = toVideoCourse(videoCourseDTO);
                videoCourse.setId(id);
                videoCourse.setUserId(userRepository.findById(videoCourseDTO.getUserId()).get());
                videoCourse.setRecTime(videoCourseRepository.findById(id).get().getRecTime());
                videoCourseRepository.save(videoCourse);
                return new ResponseEntity<>("VideoCourse was updated", HttpStatus.OK);

            }else{
                return new ResponseEntity<>("Please write correctly user id", HttpStatus.BAD_REQUEST);
            }

        }else {
            return new ResponseEntity<>("VideoCourse with id " + id + " wasn't found", HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<?> deleteVideoCourse(Long id){
        if(videoCourseRepository.existsById(id)){
            videoCourseRepository.deleteById(id);
            return ResponseEntity.ok("VideoCourse was deleted");
        }else{
            return new ResponseEntity<>("VideoCourse with id " + id + " wasn't found", HttpStatus.NOT_FOUND);
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


    private VideoCourse toVideoCourse(VideoCourseDTO videoCourseDTO){
        return VideoCourse.builder()
                .description(videoCourseDTO.getDescription())
                .rating(videoCourseDTO.getRating())
                .build();
    }

}
