package service;

import entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import repository.MemberRepository;

import java.util.List;

@Service
// spring Transactional 사용
@Transactional
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    // 멀티 쓰레드 때문에 한번에 요청 들어오면 같이 넘어갈 가능성 있기 때문에 name에 unique 걸어라
    public Long join(Member member) {
        validateDuplicateMember(member); // 중복 회원 검증
        memberRepository.save(member);
        return member.getId();
    }
    //회원 중복 검증 로직
    private void validateDuplicateMember(Member member) {
        List<Member> findMembers = memberRepository.findByName(member.getName());
        if (!findMembers.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 회원입니다");
        }
    }
    // 회원 전체 조회
    // @Transactional(readOnly = true)  읽기는 이거 쓰면 좀더 최적화
//    @Transactional(readOnly = true)
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    // 회원 단일 조회
    public Member findOne(Long id) {
        return memberRepository.findMemberById(id);
    }

}
