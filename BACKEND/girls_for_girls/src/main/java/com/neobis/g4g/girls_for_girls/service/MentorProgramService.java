package com.neobis.g4g.girls_for_girls.service;

import com.neobis.g4g.girls_for_girls.data.dto.MentorProgramDTO;
import com.neobis.g4g.girls_for_girls.data.entity.Feedback;
import com.neobis.g4g.girls_for_girls.data.entity.MentorProgram;
import com.neobis.g4g.girls_for_girls.repository.MentorProgramRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

import static com.neobis.g4g.girls_for_girls.data.dto.MentorProgramDTO.toMentorProgramDTO;

@Service
public class MentorProgramService {
    private final MentorProgramRepository mentorProgramRepository;

    @Autowired
    public MentorProgramService(MentorProgramRepository mentorProgramRepository) {
        this.mentorProgramRepository = mentorProgramRepository;
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

    public ResponseEntity<?> addMentorProgram(MentorProgramDTO mentorProgramDTO){
        mentorProgramRepository.save(toMentorProgram(mentorProgramDTO));
        return new ResponseEntity<>("Mentor program was added", HttpStatus.CREATED);
    }

    public ResponseEntity<?> updateMentorProgram(long id, MentorProgramDTO mentorProgramDTO){
        if(mentorProgramRepository.findById(id).isPresent()){
            MentorProgram mentorProgram = toMentorProgram(mentorProgramDTO);
            mentorProgram.setId(id);
            mentorProgramRepository.save(mentorProgram);
            return ResponseEntity.ok("Mentor program was updated");
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
    public static MentorProgram toMentorProgram(MentorProgramDTO mentorProgramDTO) {
        return MentorProgram.builder()
                .description(mentorProgramDTO.getDescription())
                .applicationEntities(mentorProgramDTO.getApplicationEntities())
                .userId(mentorProgramDTO.getUserId())
                .recTime(Timestamp.valueOf(LocalDateTime.now()))
                .build();
    }
}
