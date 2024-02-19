package domain.dto.response.article;

import domain.entity.Article;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ArticleResponseListDto {
    /** 카테고리별 게시글 목록 확인 */
    private Long article_id;
    private String title;
    private String content;
    private int likesCnt; //추천 수 카운트 현황
    private int commentCnt;
    private String createDate; // 글 작성 일
    private String writerName; // 글쓴이

    @Builder
    public ArticleResponseListDto(Long article_id,String title, String content, int likesCnt, int commentCnt, String createDate, String writerName) {
        this.article_id = article_id;
        this.title = title;
        this.content = content;
        this.likesCnt = likesCnt;
        this.commentCnt = commentCnt;
        this.createDate = createDate;
        this.writerName = writerName;
    }

    // db에서 article list를 가져오면 map함수로 entity -> dto로 변환작업 진행
    public static ArticleResponseListDto fromEntity(Article article){
        return ArticleResponseListDto.builder()
                .article_id(article.getId())
                .title(article.getTitle())
                .content(article.getContent())
                .likesCnt(article.getLikesCnt())
                .commentCnt(article.getCommentCnt())
                .createDate(article.getCreatedDate())
                .writerName(article.getMember().getNickname())
                .build();

    }
}
