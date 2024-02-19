package newtest.newtest.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Getter
@NoArgsConstructor
public class Comment extends TimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long id;

    //댓글 내용
    @Column(nullable = false)
    private String content;

    @Builder
    public Comment(String content, Member member, Article article) {
        this.content = content;
        this.member = member;
        this.article = article;
    }

    //사용자 다대일
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Member member;

    //게시글 다대일
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "article_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Article article;

    // 연관 관계 메서드 정의
    //다대일 관계 설정(board : comment = 1 : N)
    public void setArticle(Article article){
        this.article = article;
        article.getCommentList().add(this);
    }
    //다대일 관계 설정(member : comment = 1: N)
    public void setMember(Member member){
        this.member = member;
        member.getCommentList().add(this);
    }
}
