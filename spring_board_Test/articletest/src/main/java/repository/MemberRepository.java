package repository;

import domain.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByLoginId(String loginId); //아이디로 검색
    Optional<Member> findByNickname(String nickname); //닉네임으로 검색

}