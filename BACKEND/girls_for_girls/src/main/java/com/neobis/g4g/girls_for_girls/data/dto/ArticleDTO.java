package com.neobis.g4g.girls_for_girls.data.dto;

import com.neobis.g4g.girls_for_girls.data.entity.Article;
import com.neobis.g4g.girls_for_girls.data.entity.User;
import lombok.*;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ArticleDTO {
    private Timestamp recTime;

    private Timestamp updateTime;

    private String title;

    private String description;

    private Long viewsCount;

    private long userId;

    public static ArticleDTO toArticleDTO(Article article){
        return ArticleDTO.builder()
                .description(article.getDescription())
                .recTime(article.getRecTime())
                .title(article.getTitle())
                .updateTime(article.getUpdateTime())
                .viewsCount(article.getViewsCount())
                .userId(article.getUserId().getId())
                .build();
    }

    public static List<ArticleDTO> toArticleDTO(List<Article> articles){
        return articles.stream().map(ArticleDTO::toArticleDTO).collect(Collectors.toList());
    }

}
