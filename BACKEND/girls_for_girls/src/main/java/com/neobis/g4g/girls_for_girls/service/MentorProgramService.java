package com.neobis.g4g.girls_for_girls.service;

import com.neobis.g4g.girls_for_girls.data.dto.MentorProgramDTO;
import com.neobis.g4g.girls_for_girls.data.entity.MentorProgram;
import com.neobis.g4g.girls_for_girls.exception.NotAddedException;
import com.neobis.g4g.girls_for_girls.exception.NotUpdatedException;
import com.neobis.g4g.girls_for_girls.repository.MentorProgramApplicationRepository;
import com.neobis.g4g.girls_for_girls.repository.TrainingApplicationRepository;
import com.neobis.g4g.girls_for_girls.repository.MentorProgramRepository;
import com.neobis.g4g.girls_for_girls.repository.UserRepository;
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

import static com.neobis.g4g.girls_for_girls.data.dto.MentorProgramApplicationDTO.toMentorProgramApplicationDTO;
import static com.neobis.g4g.girls_for_girls.data.dto.MentorProgramDTO.toMentorProgramDTO;

@Service
@AllArgsConstructor
public class MentorProgramService {
    private final MentorProgramRepository mentorProgramRepository;
    private final UserRepository userRepository;
    private final MentorProgramApplicationRepository mentorProgramApplicationRepository;


    public Page<MentorProgram> getAllMentorPrograms(Pageable pageable){
        List<MentorProgram> mentorProgramDTOS = mentorProgramRepository.findAll();
        return new PageImpl<>(mentorProgramDTOS, pageable, mentorProgramDTOS.size());
    }

    public ResponseEntity<?> getMentorProgramById(long id){
        if(mentorProgramRepository.findById(id).isPresent()){
            return ResponseEntity.ok(mentorProgramRepository.findById(id).get());
        }else{
            return new ResponseEntity<>("Mentor program with id " + id + " wasn't found", HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<?> getAllApplicationsByMentorProgramId(long id){
        if(mentorProgramRepository.existsById(id)){
            return ResponseEntity.ok(toMentorProgramApplicationDTO(mentorProgramApplicationRepository.findAllByMentorProgramId(id)));
        }else {
            return new ResponseEntity<>("Mentor program with id " + id + " wasn't found", HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<?> addMentorProgram(MentorProgramDTO mentorProgramDTO,
                                              BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            throw new NotAddedException(getErrorMsg(bindingResult).toString());
        }

        if(userRepository.existsById(mentorProgramDTO.getUserId())){
            MentorProgram mentorProgram = toMentorProgram(mentorProgramDTO);
            mentorProgram.setUserId(userRepository.findById(mentorProgramDTO.getUserId()).get());
            mentorProgram.setRecTime(Timestamp.valueOf(LocalDateTime.now()));
            return ResponseEntity.ok(mentorProgramRepository.save(mentorProgram).getId());
        }else{
            return new ResponseEntity<>("Write user id correctly", HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<?> updateMentorProgram(long id, MentorProgramDTO mentorProgramDTO,
                                                 BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            throw new NotUpdatedException(getErrorMsg(bindingResult).toString());
        }

        if(mentorProgramRepository.findById(id).isPresent()){

            if(userRepository.existsById(mentorProgramDTO.getUserId())){
                MentorProgram mentorProgram = toMentorProgram(mentorProgramDTO);
                mentorProgram.setId(id);
                mentorProgram.setUserId(userRepository.findById(mentorProgramDTO.getUserId()).get());
                mentorProgram.setRecTime(mentorProgramRepository.findById(id).get().getRecTime());
                mentorProgramRepository.save(mentorProgram);
                return ResponseEntity.ok("Mentor program was updated");
            }else{
                return new ResponseEntity<>("Write user id correctly", HttpStatus.BAD_REQUEST);
            }

        }else{
            return new ResponseEntity<>("Mentor program with id " + id + " wasn't found", HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<?> deleteMentorProgram(long id){
        if(mentorProgramRepository.existsById(id)){
            mentorProgramRepository.deleteById(id);
            return  ResponseEntity.ok("Mentor program was deleted");
        }else{
            return new ResponseEntity<>("Mentor program with id " + id + " wasn't found", HttpStatus.NOT_FOUND);
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

    public static MentorProgram toMentorProgram(MentorProgramDTO mentorProgramDTO) {
        return MentorProgram.builder()
                .description(mentorProgramDTO.getDescription())
                .build();
    }
}
