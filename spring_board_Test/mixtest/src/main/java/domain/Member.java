package domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter

public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

//    @NotEmpty(message = "회원 이름은 필수 입니다.")
    private String nickname;
    private String password;
    private String email;

    private String address;

    private Integer likesCnt; // 총 추천받은 수
    /**
     * user가 작성한 글과 일대다 관계 매핑을 진행해야함
     * user가 추천을 누른 글과도 일대다 관계 매핑을 진행해야함
     * user가 작성한 댓글과도 일대다 관계 매핑을 진행해야함

     * user가 작성한 게시글 목록을 알아야함
     *
     * */

    /** 외래키를 가진 board class가 관계의 주인이 되는 것이 좋다
     * => 연관관계의 주인은 외래키를 가진 객체가 되는 것이 바람직. 보통 일대다에서 다 위치하는 객체가 관계의 주인이다.
     * 유저가 게시글을 무엇을 작성했는지도 확인해야하고, 게시글에서 누가 글을 작성했는지 확인도 해야해서 양방향 객체관계가 이루어져야한다
     * mappedBy는 객체간 관계에서 주인이 아닌 곳에 작성
     * */
    //orphanRemoval = true 특성 공부하기
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Article> articles = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, fetch = FetchType.LAZY) //회원정보 가져오기에서 작성한 댓글 전부 확인하기
    private List<Comment> commentList = new ArrayList<>();
    //추천 (일대다)
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, fetch = FetchType.LAZY) //회원정보 가져오기에서 추천 누른 게시글 전부 확인하기
    private List<Likes> likes;
}