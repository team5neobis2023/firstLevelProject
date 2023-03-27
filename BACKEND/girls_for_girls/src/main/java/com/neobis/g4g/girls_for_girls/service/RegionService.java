package com.neobis.g4g.girls_for_girls.service;

import com.neobis.g4g.girls_for_girls.data.dto.RegionDTO;
import com.neobis.g4g.girls_for_girls.repository.RegionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.neobis.g4g.girls_for_girls.data.dto.RegionDTO.toRegionDTO;
import static com.neobis.g4g.girls_for_girls.data.dto.VideoCourseCategoryDTO.toVideoCourseCategoryDTO;

@Service
public class RegionService {
    private final RegionRepository regionRepository;

    @Autowired
    public RegionService(RegionRepository regionRepository) {
        this.regionRepository = regionRepository;
    }

    public List<RegionDTO> getAllRegions(){
        return toRegionDTO(regionRepository.findAll());
    }


    public ResponseEntity<?> getRegionById(Long id) {
        if(regionRepository.findById(id).isPresent()){
            return ResponseEntity.ok(toRegionDTO(regionRepository.findById(id).get()));
        }
        return new ResponseEntity<>("Region with id " + id + " wasn't found", HttpStatus.NOT_FOUND);
    }
}
