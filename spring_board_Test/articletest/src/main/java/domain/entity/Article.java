package domain.entity;


import domain.enum_class.ArticleCategory;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.List;

@Entity
@NoArgsConstructor
@Getter
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "article_id")
    private Long id;

    @Column(nullable = false)
    private String title;

    private String content;

    @Enumerated(EnumType.STRING)
    private ArticleCategory articleCategory; // 카테고리

    //추천 수
    private int likesCnt;
    //댓글 수
    private int commentCnt;

    @Builder
    public Article(String title, String content, ArticleCategory articleCategory, Member member, int likesCnt, int commentCnt){
        this.title = title;
        this.content = content;
        this.articleCategory = articleCategory;
        this.member = member;
        this.likesCnt = 0;
        this.commentCnt = 0;
    }

    //유저
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Member member;

    //댓글(일대다)
    @OneToMany(mappedBy = "article", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Comment> commentList;

    //orphanRemoval = true VS cascade = CascadeType.REMOVE 비교 공부하기

    //추천(일대다)
    @OneToMany(mappedBy = "article", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Likes> likesList;

    /**
     public void updateBoard(BoardDto dto){
     this.title = dto.getTitle();
     this.body = dto.getBody();
     }
     */

    /** 양방향 연관관계 매핑 작업*/
    public void setMappingMember(Member member){
        this.member = member;
        member.getArticles().add(this);
    }

    public void setArticleCategory(String category){ //대소문자 관계없이 category 선별 가능
        this.articleCategory = ArticleCategory.of(category);
    }

    public void addComment(){this.commentCnt += 1;} // 댓글수 1 증가

    public void update(String title, String content){ // 게시글 수정
        this.title = title;
        this.content = content;
    }

    public void plusLikes(){this.likesCnt += 1;}
    public void minusLikes(){this.likesCnt -= 1;}
}
