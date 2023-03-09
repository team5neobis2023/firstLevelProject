package com.neobis.g4g.girls_for_girls.service;

import com.neobis.g4g.girls_for_girls.data.dto.ConferencesDTO;
import com.neobis.g4g.girls_for_girls.data.entity.Conference;
import com.neobis.g4g.girls_for_girls.repository.ConferencesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.neobis.g4g.girls_for_girls.data.dto.ConferencesDTO.toConferencesDTO;

@Service
public class ConferencesService {
    private final ConferencesRepository conferencesRepository;

    @Autowired
    public ConferencesService(ConferencesRepository conferencesRepository) {
        this.conferencesRepository = conferencesRepository;
    }

    public List<ConferencesDTO> getAllConferences(){
        return toConferencesDTO(conferencesRepository.findAll());
    }

    public ResponseEntity<?> getConferenceById(long id){
        if(conferencesRepository.findById(id).isPresent()){
            return ResponseEntity.ok(toConferencesDTO(conferencesRepository.findById(id).get()));
        }
        return new ResponseEntity<>("Conference with id " + id + " wasn't found", HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<?> addConference(ConferencesDTO conferencesDTO){
        conferencesRepository.save(toConference(conferencesDTO));
        return new ResponseEntity<>("Conference was created", HttpStatus.CREATED);
    }

    public ResponseEntity<?> updateConference(long id, ConferencesDTO conferencesDTO){
        if(conferencesRepository.findById(id).isPresent()){
            Conference conference = toConference(conferencesDTO);
            conference.setId(id);
            conferencesRepository.save(conference);
            return ResponseEntity.ok("Conference was updated");
        }else{
            return new ResponseEntity<>("Conference with id " + id + " wasn't found", HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<?> deleteApplication(Long id){
        if(conferencesRepository.existsById(id)){
            conferencesRepository.deleteById(id);
            return ResponseEntity.ok("Conference was deleted");
        }else{
            return new ResponseEntity<>("Conference with id " + id + " wasn't found", HttpStatus.NOT_FOUND);
        }
    }

    private Conference toConference(ConferencesDTO conferencesDTO){
        return Conference.builder()
                .recTime(conferencesDTO.getRecTime())
                .conferenceDate(conferencesDTO.getConferenceDate())
                .description(conferencesDTO.getDescription())
                .userId(conferencesDTO.getUserId())
                .applicationEntities(conferencesDTO.getApplicationEntities())
                .build();
    }
}
