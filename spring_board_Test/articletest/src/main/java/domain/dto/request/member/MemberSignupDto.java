package domain.dto.request.member;

import domain.entity.Member;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class MemberSignupDto {

    private String loginId;
    private String password;
    private String nickname;

    @Builder
    public MemberSignupDto(String loginId, String password, String nickname) {
        this.loginId = loginId;
        this.password = password;
        this.nickname = nickname;
    }

    //DTO -> ENTITY
    public static Member ofEntity(MemberSignupDto dto){
        return Member.builder()
                .loginId(dto.getLoginId())
                .password(dto.getPassword())
                .nickname(dto.getNickname())
                .signUpAt(LocalDateTime.now())
                .likesCnt(0)
                .build();
    }

}

