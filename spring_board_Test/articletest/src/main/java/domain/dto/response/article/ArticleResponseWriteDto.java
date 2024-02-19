package domain.dto.response.article;

import domain.entity.Article;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
public class ArticleResponseWriteDto {

    private Long article_id;
    private String title;
    private String content;
    private String writerName;
    private String createdDate;

    public ArticleResponseWriteDto(Long article_id, String title, String content, String writerName, String createdDate) {
        this.article_id = article_id;

        this.title = title;
        this.content = content;
        this.writerName = writerName;
        this.createdDate = createdDate;
    }


    public static ArticleResponseWriteDto fromEntity(Article article, String writerName) {
        return ArticleResponseWriteDto.builder()
                .article_id(article.getId())
                .title(article.getTitle())
                .content(article.getContent())
                .writerName(writerName)
                .createdDate(article.getCreatedDate())
                .build();
    }
}
