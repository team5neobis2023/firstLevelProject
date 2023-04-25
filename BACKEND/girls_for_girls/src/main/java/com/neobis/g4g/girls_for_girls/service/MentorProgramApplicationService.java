package com.neobis.g4g.girls_for_girls.service;

import com.neobis.g4g.girls_for_girls.data.dto.MentorProgramApplicationDTO;
import com.neobis.g4g.girls_for_girls.data.dto.TrainingApplicationDTO;
import com.neobis.g4g.girls_for_girls.data.entity.*;
import com.neobis.g4g.girls_for_girls.exception.NotAddedException;
import com.neobis.g4g.girls_for_girls.repository.*;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class MentorProgramApplicationService {
    private final MentorProgramApplicationRepository mentorProgramApplicationRepository;
    private final MentorProgramRepository mentorProgramRepository;
    private final NotificationRepo notificationRepo;

    public Page<MentorProgramApplication> getAllApplications(Pageable pageable) {
        List<MentorProgramApplication> mentorProgramApplications = mentorProgramApplicationRepository.findAll();
        return new PageImpl<>(mentorProgramApplications, pageable, mentorProgramApplications.size());
    }


    public ResponseEntity<?> getApplicationById(long id) {
        if(mentorProgramApplicationRepository.findById(id).isPresent()){
            return ResponseEntity.ok(mentorProgramApplicationRepository.findById(id).get());
        }
        return new ResponseEntity<>("Application with id " + id + " wasn't found", HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<?> addApplication(MentorProgramApplicationDTO applicationDTO,
                                                 User user) {

        if(mentorProgramApplicationRepository.existsByEmailAndMentorProgramId(applicationDTO.getEmail(), applicationDTO.getMentorProgram_id())){
            return ResponseEntity.badRequest().body("Application with this email already exists");
        }

        MentorProgramApplication mentorProgramApplication = toApplication(applicationDTO);
        mentorProgramApplication.setUser(user);
        return ResponseEntity.ok(mentorProgramApplicationRepository.save(mentorProgramApplication).getId());
    }

    public ResponseEntity<String> approveApplication(Long id){

        if(mentorProgramApplicationRepository.findById(id).isEmpty()) {
            return new ResponseEntity<>("Application with id " + id + " wasn't found", HttpStatus.NOT_FOUND);
        }

        MentorProgramApplication mentorProgramApplication = mentorProgramApplicationRepository.findById(id).get();
        mentorProgramApplication.setApproved(true);
        mentorProgramApplicationRepository.save(mentorProgramApplication);

        MentorProgram mentorProgram = mentorProgramApplication.getMentorProgram();

        Notification notification = new Notification();
        notification.setHeader("Одобрение заявки на тренинг");
        notification.setMessage("Поздравляем! Ваша заявка на тренинг " + mentorProgram.getHeader() + " одобрена.");
        notification.setUser(mentorProgramApplication.getUser());
        notification.setRecTime(Timestamp.valueOf(LocalDateTime.now()));
        notificationRepo.save(notification);

        return new ResponseEntity<>("Application was approved", HttpStatus.OK);
    }

    public ResponseEntity<String> deleteApplication(Long id){
        if(mentorProgramApplicationRepository.existsById(id)){
            mentorProgramApplicationRepository.deleteById(id);
            return ResponseEntity.ok("Application was deleted");
        }else{
            return new ResponseEntity<>("Application with id " + id + " wasn't found", HttpStatus.NOT_FOUND);
        }
    }

    private MentorProgramApplication toApplication(MentorProgramApplicationDTO applicationDTO) {
        return MentorProgramApplication.builder()
                .fullName(applicationDTO.getFullName())
                .dateOfBirth(applicationDTO.getDateOfBirth())
                .email(applicationDTO.getEmail())
                .phoneNumber(applicationDTO.getPhoneNumber())
                .address(applicationDTO.getAddress())
                .goals(applicationDTO.getGoals())
                .helpOfProgram(applicationDTO.getHelpOfProgram())
                .idealMentorDesc(applicationDTO.getIdealMentorDesc())
                .experienceWithMentor(applicationDTO.getExperienceWithMentor())
                .mentorProgram(mentorProgramRepository.findById(applicationDTO.getMentorProgram_id()).get())
                .whereFound(applicationDTO.getWhereFound())
                .build();
    }
}
