#### 작성일자 : 2023.04.22, [@wonjae124](https://github.com/wonjae124)
<br/><br/>
# JPA
- JPA는 SQL 쿼리문도 직접 만들어줘서 편리하다. JDBC template에서도 작성이 필요하던, 쿼리문을 JPA는 작성 해준다
- JPA를 통해서, 기존 sql, 데이터 중심 설계에서 -> 객체 중심의 설계로 패러다임 전환 가능
- JPA를 사용한 개발 생산성을 크게 높일 수 있다
- ORM(object relational database mapping), 객체와 db를 매핑한다.
<br/>
## 과정
### bundle.gradle 변경
  - implementation 'org.springframework.boot:spring-boot-starter-data-jpa' 추가 : JPA, JDBC도 포함되어 있음
- application.properties 변경
  - jpa 인터페이스의 hibernate 사용 예정
  - show-sql : sql 날리는걸 보여줌.
  - ddl-auto : create - DB생성까지 도와줌, none - DB생성 안함
  ```
  spring.jpa.show-sql=true 
  spring.jpa.hibernate.ddl-auto=none
<br/>   ```
### entity mapping 
  - main -> java -> domain 폴더 -> Member.java 파일에 @Entity, @Id, @GeneratedValue(strategy = GenerationType.IDENTITY) 추
<br/>
### JpaMemberRepository 
  -  main -> java -> hello.hellospring -> Repository에 JpaMemberRepository.java 생성
  - pk 기반인건, JPA쿼리를 작성 안해도 된다.
  - pk기반이 아닌 findByName, findAll은 JPA 쿼리를 별도로 작성해줘야 한다.
 - Service 계층 변경
  - main -> java -> hello.hellospring -> service -> Member.java
  - 데이터를 저장하고 변경할 때는, 서비스 계층 MemberService에 @Transactional 추가
 
  ```
  package hello.hellospring.repository;

  import hello.hellospring.domain.Member;

  import javax.persistence.EntityManager;
  import java.util.List;
  import java.util.Optional;


  public class JpaMemberRepository implements MemberRepository {

      private final EntityManager em;

      public JpaMemberRepository(EntityManager em) {
          this.em = em;
      }


      @Override
      public Member save(Member member) {
          em.persist(member); // 영구저장하겠다
          return member; // insert, setId까지 전부해준다.
      }

      @Override
      public Optional<Member> findById(Long id) {
          Member member = em.find(Member.class, id);
          return Optional.ofNullable(member); // em.find(조회 할 타입값, 식별값)
      }

      @Override
      public Optional<Member> findByName(String name) {
          List<Member> result = em.createQuery("select m from Member m where m.name = :name", Member.class)
                  .setParameter("name", name)
                  .getResultList();
          return result.stream().findAny();
      }

      @Override
      public List<Member> findAll() {
          return em.createQuery("select m from Member m", Member.class) // 테이블 대상이 아닌, 객체를 대상으로 쿼리를 날린다.
                  .getResultList();
      }
  }

  ```
<br/><br/>
---
정리 : https://blog.naver.com/wonjae124/223081334425<br/>
출처 : 스프링 입문 - 코드로 배우는 스프링 부트, 웹MVC, DB 접근 기술

<br/><br/>
