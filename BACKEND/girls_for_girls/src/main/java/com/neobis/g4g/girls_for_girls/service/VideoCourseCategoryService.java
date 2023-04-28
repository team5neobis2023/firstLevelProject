package com.neobis.g4g.girls_for_girls.service;

import com.neobis.g4g.girls_for_girls.data.dto.VideoCourseCategoryDTO;
import com.neobis.g4g.girls_for_girls.data.entity.VideoCourseCategory;
import com.neobis.g4g.girls_for_girls.exception.NotAddedException;
import com.neobis.g4g.girls_for_girls.exception.NotUpdatedException;
import com.neobis.g4g.girls_for_girls.repository.VideoCourseCategoryRepository;
import com.neobis.g4g.girls_for_girls.repository.VideoCourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.List;

import static com.neobis.g4g.girls_for_girls.data.dto.VideoCourseCategoryDTO.toVideoCourseCategoryDTO;
import static com.neobis.g4g.girls_for_girls.data.dto.VideoCourseDTO.toVideoCourseDTO;

@Service
public class VideoCourseCategoryService {
    private final VideoCourseRepository videoCourseRepository;
    private final VideoCourseCategoryRepository videoCourseCategoryRepository;

    @Autowired
    public VideoCourseCategoryService(VideoCourseRepository videoCourseRepository, VideoCourseCategoryRepository videoCourseCategoryRepository) {
        this.videoCourseRepository = videoCourseRepository;
        this.videoCourseCategoryRepository = videoCourseCategoryRepository;
    }

    public List<VideoCourseCategoryDTO> getAllVideoCourseCategories() {
        return toVideoCourseCategoryDTO(videoCourseCategoryRepository.findAll());
    }


    public ResponseEntity<?> getVideoCourseCategoryById(Long id) {
        if(videoCourseCategoryRepository.findById(id).isPresent()){
            return ResponseEntity.ok(toVideoCourseCategoryDTO(videoCourseCategoryRepository.findById(id).get()));
        }
        return new ResponseEntity<>("Video course category with id " + id + " wasn't found", HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<?> getAllVideoCoursesByVideoCourseCategoryId(Long id) {
        if(videoCourseCategoryRepository.existsById(id)){
            return ResponseEntity.ok(toVideoCourseDTO(videoCourseRepository.findVideoCoursesByVideoCourseCategoryId(id)));
        }else{
            return new ResponseEntity<>("VideoCourse category with id " + id + " wasn't found", HttpStatus.NOT_FOUND);
        }
    }


    public ResponseEntity<String> addVideoCourseCategory(VideoCourseCategoryDTO videoCourseCategoryDTO,
                                                    BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            throw new NotAddedException(getErrorMsg(bindingResult).toString());
        }

        videoCourseCategoryRepository.save(
                VideoCourseCategory.builder()
                .name(videoCourseCategoryDTO.getName())
                .build()
        );
        return ResponseEntity.ok("VideoCourse category was created");
    }

    public ResponseEntity<String> updateVideoCourseCategory(Long id, VideoCourseCategoryDTO videoCourseCategoryDTO,
                                                       BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            throw new NotUpdatedException(getErrorMsg(bindingResult).toString());
        }

        if(videoCourseCategoryRepository.findById(id).isPresent()){
            videoCourseCategoryRepository.save(
                    VideoCourseCategory.builder()
                            .id(id)
                            .name(videoCourseCategoryDTO.getName())
                            .build()
            );
            return new ResponseEntity<>("VideoCourse category was updated", HttpStatus.OK);
        }else{
            return new ResponseEntity<>("VideoCourse category with id " + id + " wasn't found", HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<String> deleteVideoCourseCategoryById(Long id) {
        if(videoCourseCategoryRepository.existsById(id)){
            videoCourseCategoryRepository.deleteById(id);
            return ResponseEntity.ok("VideoCourse category was deleted");
        }else{
            return new ResponseEntity<>("VideoCourse category with id " + id + " wasn't found", HttpStatus.NOT_FOUND);
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
