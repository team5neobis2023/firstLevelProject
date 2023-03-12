package com.neobis.g4g.girls_for_girls.service;

import com.neobis.g4g.girls_for_girls.data.dto.ApplicationDTO;
import com.neobis.g4g.girls_for_girls.data.dto.MentorProgramDTO;
import com.neobis.g4g.girls_for_girls.data.entity.MentorProgram;
import com.neobis.g4g.girls_for_girls.exception.NotAddedException;
import com.neobis.g4g.girls_for_girls.exception.NotUpdatedException;
import com.neobis.g4g.girls_for_girls.repository.ApplicationRepository;
import com.neobis.g4g.girls_for_girls.repository.MentorProgramRepository;
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
import static com.neobis.g4g.girls_for_girls.data.dto.MentorProgramDTO.toMentorProgramDTO;

@Service
public class MentorProgramService {
    private final MentorProgramRepository mentorProgramRepository;
    private final UserRepository userRepository;
    private final ApplicationRepository applicationRepository;

    @Autowired
    public MentorProgramService(MentorProgramRepository mentorProgramRepository, UserRepository userRepository, ApplicationRepository applicationRepository) {
        this.mentorProgramRepository = mentorProgramRepository;
        this.userRepository = userRepository;
        this.applicationRepository = applicationRepository;
    }

    public List<MentorProgramDTO> getAllMentorPrograms(){
        return toMentorProgramDTO(mentorProgramRepository.findAll());
    }

    public ResponseEntity<?> getMentorProgramById(long id){
        if(mentorProgramRepository.findById(id).isPresent()){
            return ResponseEntity.ok(toMentorProgramDTO(mentorProgramRepository.findById(id).get()));
        }else{
            return new ResponseEntity<>("Mentor program with id " + id + " wasn't found", HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<?> getAllApplicationsByMentorProgramId(long id){
        if(mentorProgramRepository.existsById(id)){
            return ResponseEntity.ok(toApplicationDTO(applicationRepository.findAllByMentorProgramId(id)));
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
            mentorProgramRepository.save(mentorProgram);
            return new ResponseEntity<>("Mentor program was added", HttpStatus.CREATED);
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
                mentorProgramRepository.save(mentorProgram);
                return new ResponseEntity<>("Mentor program was updated", HttpStatus.CREATED);
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
                .recTime(Timestamp.valueOf(LocalDateTime.now()))
                .build();
    }
}
