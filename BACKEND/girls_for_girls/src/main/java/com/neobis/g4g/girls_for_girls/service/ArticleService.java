package com.neobis.g4g.girls_for_girls.service;

import com.neobis.g4g.girls_for_girls.data.dto.ArticleDTO;
import com.neobis.g4g.girls_for_girls.data.entity.Article;
import com.neobis.g4g.girls_for_girls.exception.NotAddedException;
import com.neobis.g4g.girls_for_girls.exception.NotUpdatedException;
import com.neobis.g4g.girls_for_girls.repository.ArticleRepository;
import com.neobis.g4g.girls_for_girls.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

import static com.neobis.g4g.girls_for_girls.data.dto.ArticleDTO.toArticleDTO;
import static com.neobis.g4g.girls_for_girls.data.dto.UserDTO.toUserDTO;

@Service
public class ArticleService {
    private final ArticleRepository articleRepository;
    private final UserRepository userRepository;

    @Autowired
    public ArticleService(ArticleRepository articleRepository, UserRepository userRepository) {
        this.articleRepository = articleRepository;
        this.userRepository = userRepository;
    }

    public List<ArticleDTO> getAllArticles(){
        return toArticleDTO(articleRepository.findAll());
    }

    public ResponseEntity<?> getArticleById(long id){
        if(articleRepository.findById(id).isPresent()){
            return ResponseEntity.ok(toArticleDTO(articleRepository.findById(id).get()));
        }else{
            return new ResponseEntity<>("Article with id " + id +" wasn't found", HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<?> addArticle(ArticleDTO articleDTO,
                                        BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            throw new NotAddedException(getErrorMsg(bindingResult).toString());
        }

        if(userRepository.existsById(articleDTO.getUserId())){
            Article article = toArticle(articleDTO);
            article.setRecTime(Timestamp.valueOf(LocalDateTime.now()));
            article.setUserId(userRepository.findById(articleDTO.getUserId()).get());
            articleRepository.save(article);
            return new ResponseEntity<>("Article was created", HttpStatus.CREATED);
        }else{
            return new ResponseEntity<>("Write correctly user id", HttpStatus.BAD_REQUEST);
        }

    }

    public ResponseEntity<?> updateArticle(long id,
                                           ArticleDTO articleDTO,
                                           BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            throw new NotUpdatedException(getErrorMsg(bindingResult).toString());
        }

        if(articleRepository.existsById(id)){
            if(userRepository.existsById(articleDTO.getUserId())){
                Article article = toArticle(articleDTO);
                article.setId(id);
                article.setRecTime(articleRepository.findById(id).get().getRecTime());
                article.setUserId(userRepository.findById(articleDTO.getUserId()).get());
                article.setLikedUsers(userRepository.findUsersByLikedArticlesId(id));
                articleRepository.save(article);
                return new ResponseEntity<>("Article was updated", HttpStatus.CREATED);
            }else{
                return new ResponseEntity<>("Write correctly user id", HttpStatus.BAD_REQUEST);
            }
        }else{
            return new ResponseEntity<>("Article with id " + id + " wasn't found", HttpStatus.NOT_FOUND);
        }

    }

    public ResponseEntity<?> deleteArticleById(Long id){
        if(articleRepository.existsById(id)){
            articleRepository.deleteById(id);
            return ResponseEntity.ok("Article was deleted");
        }else{
            return new ResponseEntity<>("Article with id " + id + " wasn't found", HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<?> getAllLikedUsersByArticleId(long id){
        if(articleRepository.existsById(id)){
            return ResponseEntity.ok(toUserDTO(userRepository.findUsersByLikedArticlesId(id)));
        }else{
            return new ResponseEntity<>("Article with id " + id + " wasn't found", HttpStatus.NOT_FOUND);
        }
    }

    private Article toArticle(ArticleDTO articleDTO) {
        return Article.builder()
                .description(articleDTO.getDescription())
                .title(articleDTO.getTitle())
                .viewsCount(articleDTO.getViewsCount())
                .updateTime(Timestamp.valueOf(LocalDateTime.now()))
                .build();
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
