package jpabook.jpashop;

import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class MemberRepository {
    @PersistenceContext // 스프링부트 실행시 항상 주입해줄 내용, entitymanager다.
    private EntityManager em;

    public Long save(Member member){
        em.persist(member);
        return member.getId();
    } // 나중에 ID 조회 정도는 하려고, return 함
    public Member find(Long id) {
        return em.find(Member.class, id);
    }
}
