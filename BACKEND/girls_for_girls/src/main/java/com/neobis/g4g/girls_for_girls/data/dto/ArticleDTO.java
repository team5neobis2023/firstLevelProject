package com.neobis.g4g.girls_for_girls.data.dto;

import com.neobis.g4g.girls_for_girls.data.entity.Article;
import com.neobis.g4g.girls_for_girls.data.entity.User;
import lombok.*;

import javax.validation.constraints.NotEmpty;
import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ArticleDTO {

    private long id;

    private Timestamp recTime;

    private Timestamp updateTime;

    @NotEmpty(message = "Статья не может быть пустой")
    private String title;

    @NotEmpty(message = "Описание не может быть пустой")
    private String description;

    private Long viewsCount;

    private String image_url;

    public static ArticleDTO toArticleDTO(Article article){
        return ArticleDTO.builder()
                .id(article.getId())
                .description(article.getDescription())
                .recTime(article.getRecTime())
                .title(article.getTitle())
                .updateTime(article.getUpdateTime())
                .viewsCount(article.getViewsCount())
                .image_url(article.getImage_url())
                .build();
    }

    public static List<ArticleDTO> toArticleDTO(List<Article> articles){
        return articles.stream().map(ArticleDTO::toArticleDTO).collect(Collectors.toList());
    }

}
