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
    <img src="https://github.com/wonjae124/Java/blob/main/image/img.png">
   
   - 스프링 코어란(Spring Core)?  
      Spring Container
      Spring 프레임워크의 근간이 되는요소로 IoC(또는 DI) 기능을 지원하는 영역을 담당합니다.
      빈 저장소를 기반으로 빈 클래스들을 제어할 수 있는 기능을 지원하기도 합니다.
      빈 저장소의 경우, IOC 패턴이 적용되어 객체 구성부터 의존성 처리까지 모든 일을 처리하는 핵심적인 역할을 합니다. 
      Container는 개발자를 대신하여 Bean을 생성/관리/제거 합니다.
      Container가 Bean을 전체적으로 관리를 해주므로 덕분에, 개발자는 모듈 간에 의존 및 결합으로 인해 발생하는 문제로부터 자유로워졌습니다.
      추가로 독립적인 코드를 어노테이션 (@Bean)으로 넘겨주면 Container가 알아서 호출해줍니다.
      Container가 개발자를 대신하여 메소드(코드)를 호출 및 필요한 자원을 전달하는 설계 구조를
      IOC(Inversion Of Control / 제어 반전)이라고 합니다
      IOC는 메소드가 필요로 하는 자원들을 코드가 실행되는 타임에 전달을 하는 데, 이를 DI(Dependcy Injection / 의존성 주입)이라고 합니다.
<br/>


  - application.yaml

     ```
     spring:
       datasource:
         url: jdbc:h2:tcp://localhost/~/jpashop
         username: sa
         password:
         driver-class-name: org.h2.Driver

       jpa:
         hibernate:
           ddl-auto: create
         properties:
           hibernate:
             format_sql: true

     logging.level:
       org.hibernate.SQL: debug
       org.hibernate.type: trace

     ```
  
  - ddl-auto: create로 테이블 생성
    <img src="https://github.com/wonjae124/Java/blob/main/image/%EC%A0%9C%EB%AA%A9%20%EC%97%86%EC%9D%8C.png">
  
  - `./gradlew clean build`
  
  - 폴더이동(build -> libs) `java -jar .\jpashop-0.0.1-SNAPSHOT.jar`
  - 쿼리 파라미터 로그 남기기
    <img src="https://github.com/wonjae124/Java/blob/main/image/01.png">

## 추가 공부
### 1. Entity란?
- JPA를 사용하게 되면 엔티티 클래스를 만들게 되는데요. 엔티티는 데이터베이스에서 테이블에 대응하는 클래스인데요. 때문에 엔티티에 따라서 만들어지는 테이블의 모양도 달리지게 됩니다.
- spring-boot-starter-data-jpa 의존성을 추가하고 @Entiy 어노테이션을 붙이면 테이블과 자바 클래스가 매핑이 됩니다. 그래서 JPA에서 '하나의 엔티티 타입을 생성한다'라는 의미는 '하나의 클래스를' 작성한다는 의미가 됩니다.
- @Entity가 붙은 클래스는 JPA가 관리해주며, JPA를 사용해서 DB 테이블과 매핑할 클래스는 @Entity를 꼭 붙여야만 매핑이 가능합니다.
- 접근 제어자가 public 혹은 protected 인 기본 생성자가 필수입니다.
- 엔티티 매니저?
  엔티티 객체들을 관리하는 역할을 합니다. 여기서 관리란 Life Cycle이라고 할 수 있습니다.
엔티티 매니저는 관리하는 엔티티 객체들을 영속 컨텍스트(Persistence Context)에 넣어두고, 객체들의 생사를 관리하게 됩니다.
- JPA란?
  JPA란 Java Persistence API의 약자이며 자바의 ORM을 위한 표준 기술로 Hibernate, Spring JPA, EcliplseLink 등 과 같은 구현체가 있고 이것의 표준 인터페이스가 JPA 입니다.
- ORM(Object-Relational Mapping)이란?
  자바의 객체와 관계형 DB를 맵핑하는 것으로 DB의 특정 테이블이 자바의 객체로 맵핑되어 SQL문을 일일이 작성하지 않고 객체로 구현할 수 있도록 하는 프레임워크입니다.
- JPA와 관련 어노테이션
    - @Id
      - 해당 칼럼이 식별키(PK, Primary key)라는 것을 의미합니다.
      모든 엔티티에 반드시 @Id 지정해 주어야 합니다.
      주로 @GeneratedValue와 함께 식별키를 어떤 전략으로 생서하는지 명시합니다.
    - @GeneratedValue
      - strategy 속성과 generator 속성으로 구분됩니다.
      strategy
      AUTO: 특정 데이터베이스에 맞게 자동으로 생성되는 방식
      TABLE: 별도의 키를 생성해주는 테이블을 이용하는 방식
      SEQUENCE: 데이터베이스의 시퀀스를 이용해서 식별키 생성(오라클에서 사용)
      IDENTITY: 기본키 생성 방식 자체를 데이터베이스에 위임하는 방식
      - generator
    - @Column
      - 인스턴스 변수가 칼럼이 되기 때문에, 컬럼명을 별도로 지정하거나 컬럼의 사이즈, 제약조건들을 추가하기 위해 사용됩니다.
    - @Table
      - 클래스가 테이블이 되기 때문에 클래스의 선언부에 작성하여 테이블명을 어떻게 정할지 결정합니다. 기본적으로 @Table이 지정되지 않으면, 클래스 명으로 테이블이 생성됩니다.


 - 테스트 코드 작성시에만 @Autowired를 사용하고 실제 코드 작성시에는 생성자를 통한 객체 주입방식을 사용
  
### 2. Repository란?
- Repository는 기존 JPA를 한 단계 더 추상화시킨 인터페이스
- 엔티티만으로는 데이터베이스에 데이터를 저장하거나 조회 할 수 없다. 데이터 처리를 위해서는 실제 데이터베이스와 연동하는 JPA 리포지터리가 필요하다.
- 리포지터리는 엔티티에 의해 생성된 데이터베이스 테이블에 접근하는 메서드들(예: findAll, save 등)을 사용하기 위한 인터페이스이다. 데이터 처리를 위해서는 테이블에 어떤 값을 넣거나 값을 조회하는 등의 CRUD(Create, Read, Update, Delete)가 필요하다. 이 때 이러한 CRUD를 어떻게 처리할지 정의하는 계층이 바로 리포지터리이다.

### 3. gradlew란?
- gradle wrapper 줄여서 gradlew 는 새로운 환경에서 프로젝트를 설정할 때 java나 gradle을 설치하지 않고 바로 빌드할 수 있게 해주는 역할을 한다.
- 환경에 종속되지 않는다는 이야기다.
- gradlew 는 shell script 이며 gradlew.bat는 Window batch script 이다.
- Java 프로젝트를 CI 환경에서 빌드할 때 CI 환경을 프로젝트 빌드 환경과 매번 맞춰줄 필요가 없는 이유가 바로 Gradle Wrapper를 사용하기 때문

### 4. SOLID란?
- 1. SRP(Single responsibility principle) : 단일 책임 원칙
  - 한 클래스는 하나의 책임만 가져야 한다.
    - 예를들어, 결제 클래스가 있다치면 이 클래스는 오직 결제 기능만을 책임지고, 만약 이 클래스를 수정해야 한다면 결제에 관련된 문제일 뿐일 것이다.

- 2. OCP(Open-closed principle) : 개방-폐쇄 원칙
  - 확장에는 열려있고 변경에는 닫혀 있어야 한다.
      - 기존의 코드를 변경하지 않으면서, 기능을 추가할 수 있도록 설계가 되는 원칙을 말한다.
  - 추상화이란?
  - 다형성이란?
- 3. LSP(Liskov substitution principle) : 리스코프 치환 원칙
  - 서브 타입은 언제나 자신의 기반 타입으로 변경할 수 있어야 한다
   - 상위 타입은 항상 하위 타입으로 대체될 수 있어야 함을 의미한다. 즉, 부모 클래스가 들어갈 자리에 자식 클래스를 넣어도 역할을 하는데 문제가 없어야 한다는 의미
- 4. ISP(Interface segregation principle) : 인터페이스 분리 원칙
  - 하나의 일반적인 인터페이스보다 여러 개의 구체적인 인터페이스가 낫다.
  - 각 역할에 맞게 인터페이스를 분리하는 것이다. 인터페이스 내에 메소드는 최소한 일수록 좋다. 즉, 최소한의 기능만 제공하면서 하나의 역할에 집중하라는 뜻

  

- 5.DIP(Dependency inversion principle) : 의존관계 역전 원칙

### 단축키
  - 설정 : cltl + alt + s
<br/><br/>

reference : [Tistory, [Spring] Spring Core 설명](https://workshop-6349.tistory.com/entry/Spring-Core-Spring-Core-%EC%84%A4%EB%AA%85)
[Entity](https://velog.io/@persestitan/Spring-%EC%97%94%ED%8B%B0%ED%8B%B0-%ED%81%B4%EB%9E%98%EC%8A%A4%EB%9E%80-Entity-class)
[solid](https://dev-coco.tistory.com/142)
