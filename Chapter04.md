#### 작성일 : 2023/04/23, 작성자 : [wonjae124](https://github.com/wonjae124)
<br/><br/>
# 환경설정
- lombok 추가
  -  annotation으로 getter, setter를 만들 수 있다
  ```
  package jpabook.jpashop;


  import lombok.Getter;
  import lombok.Setter;

  @Getter @Setter
  public class Hello {
      private String data;
  }
  ```
- 의존성으로 spring web 설치하면, webMvc, Tomcat를 사용할 수 있다. 함께 따라온다.
- 의존성 JPA에는 AOP와 JDBC가 내장되어 있다
  - JDBC에 Connection pool인 HikariCP가 내장되어 있다. transcation인 tx도 내장되어 있다.
  - hibernate 내장돼 있다.

 - <img src="https://github.com/wonjae124/Java/blob/main/image/img.png">
    스프링 코어란(Spring Core)?
      Spring Container
      Spring 프레임워크의 근간이 되는요소로 IoC(또는 DI) 기능을 지원하는 영역을 담당합니다.

      빈 저장소를 기반으로 빈 클래스들을 제어할 수 있는 기능을 지원하기도 합니다.

      빈 저장소의 경우, IOC 패턴이 적용되어 객체 구성부터 의존성 처리까지 모든 일을 처리하는 핵심적인 역할을 합니다. 



      Container는 개발자를 대신하여 Bean을 생성/관리/제거 합니다.

      Container가 Bean을 전체적으로 관리를 해주므로 덕분에

      개발자는 모듈 간에 의존 및 결합으로 인해 발생하는 문제로부터 자유로워졌습니다.



      추가로 독립적인 코드를 어노테이션 (@Bean)으로 넘겨주면 Container가 알아서 호출해줍니다.



      Container가 개발자를 대신하여 메소드(코드)를 호출 및 필요한 자원을 전달하는 설계 구조를

      IOC(Inversion Of Control / 제어 반전)이라고 합니다.



      IOC는 메소드가 필요로 하는 자원들을 코드가 실행되는 타임에 전달을 하는 데,

      이를 DI(Dependcy Injection / 의존성 주입)이라고 합니다.
<br/><br/>

reference : [Tistory, [Spring] Spring Core 설명](https://workshop-6349.tistory.com/entry/Spring-Core-Spring-Core-%EC%84%A4%EB%AA%85)
