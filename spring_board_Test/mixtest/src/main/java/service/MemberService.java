package service;

import domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import repository.MemberRepository;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
// @Transactional // 자바 말고 spring꺼로 써라
public class MemberService {
    /*
     private MemberRepository memberRepository;

    setter injection 좋진 않다 생성자 injection 추천
    @Autowired
    public void setMemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }
    */

    // 생성자 injection
    private final MemberRepository memberRepository;
    /*
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }
    */

    //회원가입
    @Transactional  // (readOnly = true)를 넣으면 데이터 변경이 안된다\
    // 전체 Transactional(readonly)를 넣으면 변경 가능한 데이터는 한번 더 @Transactional 넣는다
    public Long join(Member member) {

        validateDuplicateMember(member); // 중복 회원 검증
        memberRepository.save(member);
        return member.getId();
    }

    // 실무에서는 두가지 요청이 한번에 들어오는 경우 있기 때문에 member name에 unique 걸어줘야 한다
    private void validateDuplicateMember(Member member) {
        List<Member> findMembers = memberRepository.findByname(member.getName());
        if (!findMembers.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    //회원 전체 조회
    // @Transactional(readOnly = true) // 단순 읽기
    public List<Member> findMembers(){
        return memberRepository.findAll();
    }
    // 회원 단일 조회
    // @Transactional(readOnly = true)
    public Member findOne(Long memberId) {
        return memberRepository.findOne(memberId);
    }
}
