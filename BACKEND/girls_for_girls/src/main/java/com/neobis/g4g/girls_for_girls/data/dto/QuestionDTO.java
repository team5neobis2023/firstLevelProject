package com.neobis.g4g.girls_for_girls.data.dto;

import com.neobis.g4g.girls_for_girls.data.entity.Question;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QuestionDTO {

    private Long id;

    @Size(min = 4, message = "ФИО должно содержать от 4 символов")
    private String full_name;

    @Email(message = "Некорректная электронная почта")
    private String email;

    @Size(min = 8, message = "Номер телефона должен содержать от 8 символов")
    private String phone_number;

    @Size(min = 8, message = "Вопрос должен содержать от 8 символов")
    private String question;

    public static QuestionDTO toQuestionDTO(Question question){
        return QuestionDTO.builder()
                .id(question.getId())
                .email(question.getEmail())
                .full_name(question.getFull_name())
                .question(question.getQuestion())
                .phone_number(question.getPhone_number())
                .build();
    }

    public static List<QuestionDTO> toQuestionDTO(List<Question> questions){
        return questions.stream().map(QuestionDTO::toQuestionDTO).collect(Collectors.toList());
    }
}
