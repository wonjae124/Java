package hello.hellospring.repository;

import hello.hellospring.domain.Member;

import java.util.*;

//

public class MemoryMemberRepository implements  MemberRepository {

    private static Map<Long, Member> store = new HashMap<>(); // ID, Name
    private static long sequence = 0L; // sequence는 0,1,2,...를 생성해준다. 0L은 Long이다.

    @Override
    public Member save(Member member) {
        member.setId(++sequence); // Id는 시스템이 할당해주는 번호다
        store.put(member.getId(),member);
        return member;
    }

    @Override
    public Optional<Member> findById(Long id) {
        return Optional.ofNullable(store.get(id)); // null이 반환되어도, 클라이언트에서 무엇을 할 수가 있다.
    }

    @Override
    public Optional<Member> findByName(String name) {
        return store.values().stream() // 루프를 돈다
                .filter(member -> member.getName().equals(name)) // 찾는다
                .findAny(); // 하나라도 찾으면 반환
    }

    @Override
    public List<Member> findAll() {
        return new ArrayList<>(store.values()); // 스토어에 있는 값들이 멤버이다. 아 ID는 반환 안해주는건가봐
    }

    public void clearStore() {
        store.clear();
    }
}
