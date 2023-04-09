package com.neobis.g4g.girls_for_girls.service;

import com.neobis.g4g.girls_for_girls.data.dto.RatingDTO;
import com.neobis.g4g.girls_for_girls.data.dto.VideoCourseDTO;
import com.neobis.g4g.girls_for_girls.data.entity.Rating;
import com.neobis.g4g.girls_for_girls.data.entity.User;
import com.neobis.g4g.girls_for_girls.data.entity.VideoCourse;
import com.neobis.g4g.girls_for_girls.exception.NotAddedException;
import com.neobis.g4g.girls_for_girls.exception.NotUpdatedException;
import com.neobis.g4g.girls_for_girls.repository.RatingRepository;
import com.neobis.g4g.girls_for_girls.repository.VideoCourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.List;

@Service
public class RatingService {
    private final RatingRepository ratingRepository;
    private final VideoCourseRepository videoCourseRepository;

    @Autowired
    public RatingService(RatingRepository ratingRepository, VideoCourseRepository videoCourseRepository) {
        this.ratingRepository = ratingRepository;
        this.videoCourseRepository = videoCourseRepository;
    }

    public ResponseEntity<?> addRating(RatingDTO ratingDTO,
                                       BindingResult bindingResult,
                                       User user) {
        if(bindingResult.hasErrors()){
            throw new NotAddedException(getErrorMsg(bindingResult).toString());
        }

        if(videoCourseRepository.existsById(ratingDTO.getVideoCourseId())){
            if(ratingRepository.findRatingByVideoCourseIdAndUserId(ratingDTO.getVideoCourseId(), user.getId()).isEmpty()) {
                Rating rating = new Rating();
                VideoCourse videoCourse = videoCourseRepository.findById(ratingDTO.getVideoCourseId()).get();
                rating.setRating(ratingDTO.getRating());
                rating.setUser(user);
                rating.setVideoCourse(videoCourse);
                ratingRepository.save(rating);
                List<Rating> ratings = ratingRepository.findAllByVideoCourseId(ratingDTO.getVideoCourseId());
                int rateSum = 0;
                for (Rating r : ratings) {
                    rateSum += r.getRating();
                }
                int courseRating = rateSum / ratings.size();
                videoCourse.setRating(courseRating);
                videoCourseRepository.save(videoCourse);
                return ResponseEntity.ok("Rating was added");
            }else{
                return new ResponseEntity<>("This user has already rated", HttpStatus.BAD_REQUEST);
            }
        }else{
            return new ResponseEntity<>("Please write correctly video course id", HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<String> updateRating(RatingDTO ratingDTO,
                                               BindingResult bindingResult,
                                               User user){
        if(bindingResult.hasErrors()){
            throw new NotUpdatedException(getErrorMsg(bindingResult).toString());
        }

        if(videoCourseRepository.existsById(ratingDTO.getVideoCourseId())
                && ratingRepository.findRatingByVideoCourseIdAndUserId(ratingDTO.getVideoCourseId(), user.getId()).isPresent()){
            Rating rating = ratingRepository.findRatingByVideoCourseIdAndUserId(ratingDTO.getVideoCourseId(), user.getId()).get();
            VideoCourse videoCourse = videoCourseRepository.findById(ratingDTO.getVideoCourseId()).get();
            rating.setRating(ratingDTO.getRating());
            rating.setUser(user);
            ratingRepository.save(rating);

            List<Rating> ratings = ratingRepository.findAllByVideoCourseId(ratingDTO.getVideoCourseId());
            int rateSum=0;
            for (Rating r : ratings) {
                rateSum += r.getRating();
            }
            int courseRating = rateSum/ratings.size();
            videoCourse.setRating(courseRating);
            videoCourseRepository.save(videoCourse);

            return ResponseEntity.ok("Rating was updated");
        }else{
            return new ResponseEntity<>("Please write correctly video course id", HttpStatus.BAD_REQUEST);
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
