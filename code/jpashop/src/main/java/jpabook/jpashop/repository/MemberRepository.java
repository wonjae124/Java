package jpabook.jpashop.repository;

import jpabook.jpashop.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class MemberRepository {
    private final EntityManager em;

    public void save(Member member) {
        em.persist(member);
    }

    public Member findOne(Long id) {
        return em.find(Member.class, id);// id를 넘기면 멤버를 찾아서 반환해준다..
    }

    public List<Member> findAll() {
        // sql은 테이블을 대상으로 쿼리하지만, Entity manager로는 entity(객체)를 대상으로 쿼리한다.
        return em.createQuery("select m from Member m", Member.class)
                .getResultList();
    }

    public List<Member> findByName(String name){
        return em.createQuery("select m from Member m where m.name = :name", Member.class) // 파라미터 정의
                .setParameter("name",name)  // 파라미터 바인딩
                .getResultList();
    }

}
