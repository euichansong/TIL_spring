package entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
@Entity
@NoArgsConstructor

public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Integer age;

    @Column(nullable = false)
    private String address;

    @Builder
    public Member(String name, Integer age, String address) {
        this.name = name;
        this.age = age;
        this.address = address;
    }
    /**
     * user가 작성한 글과 일대다 관계 매핑을 진행해야함
     * user가 추천을 누른 글과도 일대다 관계 매핑을 진행해야함
     * user가 작성한 댓글과도 일대다 관계 매핑을 진행해야함
     * user가 작성한 게시글 목록을 알아야함
     *
     * */
    // 작성 글 일대다 , article 테이블에 있는 member 필드에 매핑
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Article> articles = new ArrayList<>();

    // 작성 댓글 일대다, comment 테이블에 있는 member 필드에 매핑
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, fetch = FetchType.LAZY) //회원정보 가져오기에서 작성한 댓글 전부 확인하기
    private List<Comment> commentList = new ArrayList<>();

    //추천 (일대다)
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, fetch = FetchType.LAZY) //회원정보 가져오기에서 추천 누른 게시글 전부 확인하기
    private List<Likes> likes;

    // 연관 관계 편의 메서드


}
