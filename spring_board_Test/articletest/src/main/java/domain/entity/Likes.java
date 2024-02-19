package domain.entity;

import common.TimeEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
@Entity
@NoArgsConstructor
@Getter
public class Likes extends TimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 하나의 게시글에 여러 좋아요 가능하므로 다대일 관계
     * 한 멤버가 여러 좋아요 누를 수 있으므로 다대일 관계
     */

    // 좋아요와 게시글 관계 다대일
    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Article article;

    // 좋아요와 사용자 관계 다대일
    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Member member;

    @Builder
    public Likes(Article article, Member member) {
        this.article = article;
        this.member = member;
    }

}