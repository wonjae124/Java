package jpabook.jpashop.repository;

import jpabook.jpashop.domain.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class OrderRepository {
   public final EntityManager em;

   public void save(Order order) {
       em.persist(order);
   }

   public Order findOne(Long id){
       return em.find(Order.class, id);
   }

   // JPA는 table을 객체로 표현한다. 아래 qrString 문법은 JPA 문서를 참고하면 된다
   public List<Order> findAllByString(OrderSearch orderSearch){
       String jpql = "select o from Order o join o.member m"; // jpql을 동적으로 만들기
       boolean isFirstCondition = true;

       //주문 상태 검색, 실무에서는 아래와 같이 안함
       if (orderSearch.getOrderStatus() != null) {
           if (isFirstCondition) {
               jpql += " where";
               isFirstCondition = false;
           } else {
               jpql += " and";
           }
           jpql += " o.status = :status";
       }

       //회원 이름 검색
       if (StringUtils.hasText(orderSearch.getMemberName())) {
           if (isFirstCondition) {
               jpql += " where";
               isFirstCondition = false;
           } else {
               jpql += " and";
           }
           jpql += " m.name like :name";
       }

       TypedQuery<Order> query = em.createQuery(jpql, Order.class)

/*
               .setParameter("status", orderSearch.getOrderStatus()) // 파라미터 바인딩, " where o.status = :status"
               .setParameter("name", orderSearch.getMemberName()) //                " and m.name like :name"
               .setFirstResult(100)// 페이징 100부터 시작해서 max(최대) 1000까지
*/
               .setMaxResults(1000);// 결과 제한

       if (orderSearch.getOrderStatus() != null) {
           query = query.setParameter("status", orderSearch.getOrderStatus());
       }
       if (StringUtils.hasText(orderSearch.getMemberName())) {
           query = query.setParameter("name", orderSearch.getMemberName());
       }

       return query.getResultList();
   }

   // 동적 쿼리를 빌드
   /**
    *JPA Criteria
    */
   public List<Order> findAllByCriteria(OrderSearch orderSearch){
       CriteriaBuilder cb = em.getCriteriaBuilder();
       CriteriaQuery<Order> cq = cb.createQuery(Order.class);
       Root<Order> o = cq.from(Order.class);
       Join<Object, Object> m = o.join("member", JoinType.INNER);

       List<Predicate> criteria = new ArrayList<>();

       // 주문 상태 검색
       if (orderSearch.getOrderStatus() != null){
           Predicate status = cb.equal(o.get("status"), orderSearch.getOrderStatus());
           criteria.add(status);
       }

       // 회원 이름 검색
       if (StringUtils.hasText(orderSearch.getMemberName())) {
           Predicate name =
                   cb.like(m.<String>get("name"), "%" +
                           orderSearch.getMemberName() + "%");
           criteria.add(name);
       }

       cq.where(cb.and(criteria.toArray(new Predicate[criteria.size()])));
       TypedQuery<Order> query = em.createQuery(cq).setMaxResults(1000);
       return query.getResultList();


   }

}
