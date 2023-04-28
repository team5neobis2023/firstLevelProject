package com.neobis.g4g.girls_for_girls.service;

import com.neobis.g4g.girls_for_girls.data.dto.SizeDTO;
import com.neobis.g4g.girls_for_girls.data.entity.Size;
import com.neobis.g4g.girls_for_girls.exception.NotAddedException;
import com.neobis.g4g.girls_for_girls.repository.SizeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.List;

@Service
public class SizeService {
    private final SizeRepository sizeRepository;

    @Autowired
    public SizeService(SizeRepository sizeRepository) {
        this.sizeRepository = sizeRepository;
    }

    public List<Size> getAllSizes(){
        return sizeRepository.findAll();
    }

    public ResponseEntity<?> getSizeById(long id){
        if(sizeRepository.existsById(id)){
            return ResponseEntity.ok(sizeRepository.findById(id).get());
        }else{
            return new ResponseEntity<>("Size with id " + id + " wasn't found", HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<String> addSize(SizeDTO sizeDTO,
                                          BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            throw new NotAddedException(getErrorMsg(bindingResult).toString());
        }

        if(!sizeRepository.existsByName(sizeDTO.getName())) {
            sizeRepository.save(Size.builder()
                            .name(sizeDTO.getName())
                    .build());
            return ResponseEntity.ok("Size was added");
        }else{
            return new ResponseEntity<>("Size with this name already exists", HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<String> deleteSize(long id){
        if(sizeRepository.existsById(id)){
            sizeRepository.deleteById(id);
            return ResponseEntity.ok("Size was deleted");
        }else{
            return new ResponseEntity<>("Size with id " + id + " wasn't found", HttpStatus.BAD_REQUEST);
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
