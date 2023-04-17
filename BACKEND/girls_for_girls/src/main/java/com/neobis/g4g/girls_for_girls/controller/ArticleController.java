package com.neobis.g4g.girls_for_girls.controller;

import com.neobis.g4g.girls_for_girls.data.dto.ArticleDTO;
import com.neobis.g4g.girls_for_girls.data.entity.User;
import com.neobis.g4g.girls_for_girls.repository.UserRepository;
import com.neobis.g4g.girls_for_girls.service.ArticleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/articles")
@Tag(
        name = "Контроллер для управления постами",
        description = "В этом контроллере вы можете добавлять, удалять, получать, а также обновлять данные постов"
)
public class ArticleController {
    private final ArticleService articleService;
    private final UserRepository userRepository;

    @Autowired
    public ArticleController(ArticleService articleService, UserRepository userRepository) {
        this.articleService = articleService;
        this.userRepository = userRepository;
    }

    @SecurityRequirement(name = "JWT")
    @GetMapping()
    @Operation(summary = "Получение всех постов", tags = "Пост")
    public List<ArticleDTO> getAllArticles(){
        return articleService.getAllArticles();
    }

    @SecurityRequirement(name = "JWT")
    @GetMapping("/{id}")
    @Operation(summary = "Получение поста по айди", tags = "Пост")
    public ResponseEntity<?> getArticleById(@PathVariable("id")
                                            @Parameter(description = "Идентификатор поста") long id){
        return articleService.getArticleById(id);
    }

    @SecurityRequirement(name = "JWT")
    @PostMapping()
    @Operation(summary = "Добавление поста", tags = "Пост")
    public ResponseEntity<?> addArticle(@RequestBody @Valid ArticleDTO articleDTO,
                                        BindingResult bindingResult){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepository.findByEmail(((UserDetails)principal).getUsername()).get();
        return articleService.addArticle(articleDTO, bindingResult, user);
    }

    @SecurityRequirement(name = "JWT")
    @PutMapping("/{id}")
    @Operation(summary = "Обновление данных поста", tags = "Пост")
    public ResponseEntity<?> updateArticle(@PathVariable("id")
                                           @Parameter(description = "Идентификатор поста") long id,
                                           @RequestBody @Valid ArticleDTO articleDTO,
                                           BindingResult bindingResult){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepository.findByEmail(((UserDetails)principal).getUsername()).get();
        return articleService.updateArticle(id, articleDTO, bindingResult, user);
    }

    @SecurityRequirement(name = "JWT")
    @DeleteMapping("/{id}")
    @Operation(summary = "Удаление поста по айди", tags = "Пост")
    public ResponseEntity<?> deleteArticleById(@PathVariable("id")
                                                   @Parameter(description = "Идентификатор поста") long id){
        return articleService.deleteArticleById(id);
    }

    @SecurityRequirement(name = "JWT")
    @GetMapping("/{id}/likedUsers")
    @Operation(summary = "Получение пользователей лайкнувших пост по айди поста", tags = "Пост")
    public ResponseEntity<?> getAllLikedUsersByArticleId(@PathVariable("id") @Parameter(description = "Идентификатор поста") long id){
        return articleService.getAllLikedUsersByArticleId(id);
    }
}
