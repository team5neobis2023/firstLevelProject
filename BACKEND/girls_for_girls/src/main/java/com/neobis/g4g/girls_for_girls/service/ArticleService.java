package com.neobis.g4g.girls_for_girls.service;

import com.neobis.g4g.girls_for_girls.data.dto.ArticleDTO;
import com.neobis.g4g.girls_for_girls.data.entity.Article;
import com.neobis.g4g.girls_for_girls.data.entity.User;
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
                                        BindingResult bindingResult,
                                        User user){
        if(bindingResult.hasErrors()){
            throw new NotAddedException(getErrorMsg(bindingResult).toString());
        }

            Article article = toArticle(articleDTO);
            article.setRecTime(Timestamp.valueOf(LocalDateTime.now()));
            article.setUser(user);
            article.setViewsCount(0L);
            articleRepository.save(article);
            return ResponseEntity.ok("Article was created");

    }

    public ResponseEntity<?> updateArticle(long id,
                                           ArticleDTO articleDTO,
                                           BindingResult bindingResult,
                                           User user){
        if(bindingResult.hasErrors()){
            throw new NotUpdatedException(getErrorMsg(bindingResult).toString());
        }

        if(articleRepository.existsById(id)){
                Article article = toArticle(articleDTO);
                article.setId(id);
                article.setRecTime(articleRepository.findById(id).get().getRecTime());
                article.setUser(user);
                article.setLikedUsers(userRepository.findUsersByLikedArticlesId(id));
                articleRepository.save(article);
                return ResponseEntity.ok("Article was updated");
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
            return ResponseEntity.ok(userRepository.findUsersByLikedArticlesId(id));
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
