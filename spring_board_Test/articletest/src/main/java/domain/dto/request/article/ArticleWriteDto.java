package domain.dto.request.article;

import domain.entity.Article;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ArticleWriteDto {

    private String title;
    private String content;

    @Builder
    public ArticleWriteDto(String title, String content) {
        this.title = title;
        this.content = content;
    }


    public static Article ofEntity(ArticleWriteDto articleWriteDto){
        return Article.builder()
                .title(articleWriteDto.title)
                .content(articleWriteDto.content)
                .commentCnt(0)
                .likesCnt(0)
                .build();
    }

    public static Article applyCategory(ArticleWriteDto articleWriteDto, String category){
        Article article = ArticleWriteDto.ofEntity(articleWriteDto);
        article.setArticleCategory(category);
        return article;
    }
}

