package com.neobis.g4g.girls_for_girls.service;

import com.neobis.g4g.girls_for_girls.data.dto.ApplicationDTO;
import com.neobis.g4g.girls_for_girls.repository.ApplicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.neobis.g4g.girls_for_girls.data.dto.ApplicationDTO.toApplicationDTO;

@Service
public class ApplicationService {
    private final ApplicationRepository applicationRepository;

    @Autowired
    public ApplicationService(ApplicationRepository applicationRepository) {
        this.applicationRepository = applicationRepository;
    }


    public List<ApplicationDTO> getAllApplications() {
        return toApplicationDTO(applicationRepository.findAll());
    }


    public ResponseEntity<?> getApplicationById(long id) {
        if(applicationRepository.existsById(id)){
            return ResponseEntity.ok(applicationRepository.findById(id));
        }
        return new ResponseEntity<>("Application with id " + id + " wasn't found", HttpStatus.NOT_FOUND);
    }

//    public ResponseEntity<?> addApplication(ApplicationDTO applicationDTO) {
        //TODO
//    }
}
