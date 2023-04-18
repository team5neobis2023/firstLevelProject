package com.neobis.g4g.girls_for_girls.service;

import com.neobis.g4g.girls_for_girls.data.dto.QuestionDTO;
import com.neobis.g4g.girls_for_girls.data.entity.Question;
import com.neobis.g4g.girls_for_girls.exception.NotAddedException;
import com.neobis.g4g.girls_for_girls.repository.QuestionRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

import static com.neobis.g4g.girls_for_girls.data.dto.QuestionDTO.toQuestionDTO;

@Service
@AllArgsConstructor
public class QuestionService {
    private final QuestionRepository questionRepository;

    public List<QuestionDTO> getAllQuestions(){
        return toQuestionDTO(questionRepository.findAll());
    }

    public ResponseEntity<?> getQuestionById(long id){
        if(questionRepository.existsById(id)){
            return ResponseEntity.ok(questionRepository.findById(id).get());
        }else{
            return ResponseEntity.badRequest().body("Question with id " + id + " wasn't found");
        }
    }

    public ResponseEntity<String> addQuestion(QuestionDTO questionDTO,
                                              BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            throw new NotAddedException(getErrorMsg(bindingResult).toString());
        }

        questionRepository.save(Question.builder()
                        .email(questionDTO.getEmail())
                        .phone_number(questionDTO.getPhone_number())
                        .full_name(questionDTO.getFull_name())
                        .question(questionDTO.getQuestion())
                        .rec_time(Timestamp.valueOf(LocalDateTime.now()))
                .build());
        return ResponseEntity.ok("Question was added");
    }

    public ResponseEntity<String> deleteQuestion(long id){
        if(questionRepository.existsById(id)){
            questionRepository.deleteById(id);
            return ResponseEntity.ok("Question was deleted");
        }else{
            return ResponseEntity.badRequest().body("Question with id " + id + " wasn't found");
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
