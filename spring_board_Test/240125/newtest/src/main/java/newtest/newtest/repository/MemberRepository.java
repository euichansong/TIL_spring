package newtest.newtest.repository;

import newtest.newtest.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemberRepository extends JpaRepository<Member, Long> {

    /*
    find: 데이터를 찾기 위한 키워드.
Member: 대상 엔티티 클래스의 이름.
ById: id 필드를 기반으로 검색한다는 의미
     */
    Member findMemberById(Long id);

    List<Member> findByName(String name);
}
