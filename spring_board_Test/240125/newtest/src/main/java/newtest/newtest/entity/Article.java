package newtest.newtest.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Article extends TimeEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "article_id")
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @Enumerated(EnumType.STRING)
    private ArticleCategory articleCategory;

    //추천 수
    private int likesCnt;
    //댓글 수
    private int commentCnt;

    @Builder
    public Article(String title, String content, ArticleCategory articleCategory,
                   Member member, int likesCnt, int commentCnt) {
        this.title = title;
        this.content = content;
        this.articleCategory = articleCategory;
        this.member = member;
        this.likesCnt = 0;
        this.commentCnt = 0;

    }
    //사용자 다대일
    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    //댓글(일대다)
    @OneToMany(mappedBy = "article", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Comment> commentList;

    //추천(일대다)
    @OneToMany(mappedBy = "article", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Likes> likesList;

    /** 양방향 연관관계 매핑 작업*/
    public void setMember(Member member){
        this.member = member;
        member.getArticles().add(this);
    }

    //대소문자 관계없이 category 선별 가능
    public void setArticleCategory(String category){
        this.articleCategory = ArticleCategory.of(category);
    }
    // 댓글수 1 증가
    public void addComment(){
        this.commentCnt += 1;
    }
    // 게시글 수정
//    public void update(String title, String content){
//        this.title = title;
//        this.content = content;
//    }

    // 좋아요 1 증가
    public void plusLikes(){
        this.likesCnt += 1;
    }

    // 좋아요 1 감소
    public void minusLikes(){
        this.likesCnt -= 1;
    }
}
