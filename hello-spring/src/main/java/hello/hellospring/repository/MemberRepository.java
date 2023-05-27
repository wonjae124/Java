package hello.hellospring.repository;

import hello.hellospring.domain.Member;

import java.util.List;
import java.util.Optional;
public interface MemberRepository {
    Member save(Member member); // 기능1. 회원을 저장소에 저장
    Optional<Member> findById(Long id); // 기능2. 방금 ID로 회원을 찾는다.
    Optional<Member> findByName(String name); // 기능3. Name으로 회원을 찾는다.
    List<Member> findAll(); // 기능4. 저장된 회원 정보를 모두 리스트로 꺼낸다

}
