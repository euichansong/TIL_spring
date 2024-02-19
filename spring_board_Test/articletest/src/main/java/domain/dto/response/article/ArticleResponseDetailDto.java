package domain.dto.response.article;

import domain.dto.response.comment.CommentResponseDto;
import domain.entity.Article;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class ArticleResponseDetailDto {

    private Long article_id;
    private String title;
    private String content;
    private int commentCnt; //댓글 수
    private int likesCnt; //추천 수
    private String createdDate;
    private String modifiedDate;
    private String writer;

    // 댓글 연관관계
    private List<CommentResponseDto> comments;

    @Builder
    public ArticleResponseDetailDto(
            Long article_id, String title, String content, int commentCnt,
            int likesCnt, String createdDate, String modifiedDate, String writer, List<CommentResponseDto> comments) {
        this.article_id = article_id;
        this.title = title;
        this.content = content;
        this.commentCnt = commentCnt;
        this.likesCnt = likesCnt;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
        this.writer = writer;
        this.comments = comments;
    }

    public static ArticleResponseDetailDto fromEntity(Article article){
        return ArticleResponseDetailDto.builder()
                .article_id(article.getId())
                .title(article.getTitle())
                .content(article.getContent())
                .writer(article.getMember().getNickname())
                .commentCnt(article.getCommentCnt())
                .likesCnt(article.getLikesCnt())
                .createdDate(article.getCreatedDate())
                .modifiedDate(article.getModifiedDate())
                .comments(article.getCommentList().stream()
                        .map(CommentResponseDto::fromEntity)
                        .collect(Collectors.toList()))
                .build();
    }
}
