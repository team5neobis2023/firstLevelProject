package com.neobis.g4g.girls_for_girls.service;

import com.neobis.g4g.girls_for_girls.data.entity.Region;
import com.neobis.g4g.girls_for_girls.repository.RegionRepository;
import com.neobis.g4g.girls_for_girls.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class RegionService {
    private final RegionRepository regionRepository;
    private final UserRepository userRepository;

    @Autowired
    public RegionService(RegionRepository regionRepository, UserRepository userRepository) {
        this.regionRepository = regionRepository;
        this.userRepository = userRepository;
    }

    public List<Region> getAllRegions(){
        return regionRepository.findAll();
    }


    public ResponseEntity<?> getRegionById(Long id) {
        if(regionRepository.findById(id).isPresent()){
            return ResponseEntity.ok(regionRepository.findById(id).get());
        }
        return new ResponseEntity<>("Region with id " + id + " wasn't found", HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<?> getAllUsersByRegionId(Long id) {
        if(regionRepository.existsById(id)){
            return ResponseEntity.ok(userRepository.findUsersByRegionId(id));
        }else{
            return new ResponseEntity<>("Region with id " + id + " wasn't found", HttpStatus.NOT_FOUND);
        }
    }
}
