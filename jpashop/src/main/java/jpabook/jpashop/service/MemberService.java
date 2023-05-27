package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
 // JPA에서 데이터변경은 트랜잭션 안에서 실행 되야한다.
@Transactional(readOnly = true) // 검색, 조회시에 성능이 개선된다. 단 추가할 때는 true로 넣으면 안 된다.
@RequiredArgsConstructor // final의 필드만 가지고, 생성자 주입해준다. 편하다.
// @AllArgsConstructor // 생성자 주입을 만들어주는 rombok 기능
public class MemberService {

    // 변경할 일이 없는 필드는 final 지정, 컴파일 시점에 점검 가능
    private final MemberRepository memberRepository;

    // 생성자 주입, set을 통해서 뭔가를 바꿀 수 없어서 좋다
    //    @Autowired
    //    public MemberService(MemberRepository memberRepository) {
    //        this.memberRepository = memberRepository;
    //    }


    // setter injection generated
    //    @Autowired
    //    public void setMemberRepository(MemberRepository memberRepository) {
    //        this.memberRepository = memberRepository;
    //    }

    /**
     * 회원 가입
     */
    @Transactional
    public Long join(Member member){
        validateDuplicateMember(member); // 중복 회원 검증
        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicateMember(Member member){
        List<Member> findMembers = memberRepository.findByName(member.getName());
        //EXCEPITON
        if (!findMembers.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    // 회원 전체 조회
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    // ID입력 받아서 회원 한명 조회
    public Member findOne(Long memberId){
        return memberRepository.findOne(memberId);
    }
}
