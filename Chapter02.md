#### 작성일 : 2023/04/22, 작성자 [wonjae124](https://github.com/wonjae124)
<br/><br/>
# SpringDataJpaMemberRepository 
- 인터페이스 만들기
- 인터페이스가 인터페이스를 받을 때는, extends를 사용
- 인터페이스는 다중 상속이 가능
- <br/>
```
package hello.hellospring.repository;

import hello.hellospring.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SpringDataJpaMemberRepository extends JpaRepository<Member, Long>, MemberRepository {
    @Override
    Optional<Member> findByName(String name);

}
```
<br/>
실무에서는 JPA와 스프링 데이터 JPA를 기본으로 사용한다.
복잡한 다중 쿼리는 Querydsl 라이브러리를 사용할 수 있다. 동적 쿼리도 가능하다.
<br/><br/>
