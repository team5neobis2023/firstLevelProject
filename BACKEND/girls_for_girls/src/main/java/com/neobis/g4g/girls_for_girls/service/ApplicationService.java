package com.neobis.g4g.girls_for_girls.service;

import com.neobis.g4g.girls_for_girls.data.dto.ApplicationDTO;
import com.neobis.g4g.girls_for_girls.data.entity.Application;
import com.neobis.g4g.girls_for_girls.repository.ApplicationRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.neobis.g4g.girls_for_girls.data.dto.ApplicationDTO.toApplicationDTO;

@Service
public class ApplicationService {
    private final ApplicationRepository applicationRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public ApplicationService(ApplicationRepository applicationRepository, ModelMapper modelMapper) {
        this.applicationRepository = applicationRepository;
        this.modelMapper = modelMapper;
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

    public ResponseEntity<?> addApplication(ApplicationDTO applicationDTO) {
        if(applicationRepository.existsByEmail(applicationDTO.getEmail())){
            return ResponseEntity.badRequest().body("Application with this email already exists");
        }
        try {
            Application application = convertToApplication(applicationDTO);
            applicationRepository.save(application);
            return new ResponseEntity<>("Application was created", HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>("Application wasn't created", HttpStatus.BAD_REQUEST);
        }

    }

    public ResponseEntity<?> updateApplication(Long id, ApplicationDTO applicationDTO){
        if(applicationRepository.existsById(id)){
            Application application = convertToApplication(applicationDTO);
            application.setId(applicationRepository.findById(id).get().getId());
            applicationRepository.save(application);
            return ResponseEntity.ok("Application was updated");
        }else {
            return new ResponseEntity<>("Application with id " + id + " wasn't found", HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<?> deleteApplication(Long id){
        if(applicationRepository.existsById(id)){
            applicationRepository.deleteById(id);
            return ResponseEntity.ok("Application was deleted");
        }else{
            return new ResponseEntity<>("Application with id " + id + " wasn't found", HttpStatus.NOT_FOUND);
        }
    }

    private Application convertToApplication(ApplicationDTO applicationDTO) {
        return modelMapper.map(applicationDTO, Application.class);
    }
}
