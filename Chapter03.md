#### 작성일 : 2023/04/23, 작성자 : [wonjae124](https://github.com/wonjae124)

<br/><br/>
# AOP 
- 모든 메서드의 호출 시간 측정
- 공통 관심사항(시간 측정, cross-cutting concern)), 핵심 관심사항(비즈니스 로직, core concern)
- 문제, 핵심 비즈니스 로직과 공통 관심사항이 섞이면 유지보수가 어렵다.
<br/>
- MemberService
  
  ```
      public Long join(Member member) {
          long start = System.currentTimeMillis();

          try {
              validateDuplicateMember(member); //중복 회원 검증
              memberRepository.save(member);
              return member.getId();
          } finally {
              long finish = System.currentTimeMillis();
              long timeMs = finish - start;
              System.out.println("join = "+ timeMs + "ms");
          }
      }

      private void validateDuplicateMember(Member member) {
          memberRepository.findByName(member.getName())
                  .ifPresent(m -> {
                      throw new IllegalStateException("이미 존재하는 회원입니다.");
                  });
      }
  ```
  
  - findMembers
    ```
        public List<Member> findMembers() {
          long start = System.currentTimeMillis();
          try {
              return memberRepository.findAll();
          } finally {
              long finish = System.currentTimeMillis();
              long timeMs = finish - start;
              System.out.println("findMembers "+timeMs+"ms");
          }
      }
    ```
  
![image](https://user-images.githubusercontent.com/67944072/233813257-824e4256-b6a0-42f3-b6be-e9085d30d4dc.png)
<br/><br/>

# AOP 
- core concern과 cross-cutting conern을 분리할 수 있다.
- Aspect Oriented Programming
- 이번 강의는 Proxy 기반 AOP
  ```
  package hello.hellospring.aop;

  import org.aspectj.lang.ProceedingJoinPoint;
  import org.aspectj.lang.annotation.Around;
  import org.aspectj.lang.annotation.Aspect;
  import org.springframework.stereotype.Component;

  @Aspect // 적어줘야 필수
  @Component
  public class TimeTraceAop {
      @Around("execution(* hello.hellospring..*(..))") // 타게팅
      public Object execute(ProceedingJoinPoint joinPoint) throws Throwable {
          long start = System.currentTimeMillis();

          System.out.println("Start: " + joinPoint.toString()); // 어떤 메서드를 콜하는지 읽을 수 있다.
          try {
              return joinPoint.proceed();            // 그 다음으로 진행한다.

          } finally {
              long finish = System.currentTimeMillis();
              long timeMs = finish - start;
              System.out.println("END: " + joinPoint.toString() + " " + timeMs + "ms"); // 어떤 메서드를 콜하는지 읽을 수 있다.
          }
      }
  }

  ```
- (issue) 빈 순환충돌
  ```
  Description:

  The dependencies of some of the beans in the application context form a cycle:

  ┌──->──┐
  |  timeTraceAop defined in class path resource [hello/hellospring/SpringConfig.class]
  └──<-──┘


  Action:

    Relying upon circular references is discouraged and they are prohibited by default.   Update your application to remove the dependency cycle between beans. As a last resort, it may be possible to break the cycle automatically by setting spring.main.allow-circular-references to true.
  ```

  solution : springconfig.java의 @Bean TimeTraceAop 클래스 전체 삭제
  
  - @Component를 설정해줘야지 빈으로 등록하면서 정상적으로, 아래 내용 출력된다
    
    ```
    Start: execution(MemberService hello.hellospring.SpringConfig.memberService())
    END: execution(MemberService hello.hellospring.SpringConfig.memberService()) 43ms
    ```

---
<br/><br/>

### 00. 컴포넌트 ( Component )
- 컴포넌트는 독립적인 단위 모듈이다. 유저가 사용하는 시스템에 대한 조작장치를 이야기한다.
- @Component 란 
  개발자가 직접 작성한 Class 를 Bean 으로 만드는 것이다.싱글톤 클래스 빈을 생성하는 어노테이션이다. 물론 @Scope를 통해 싱글톤이 아닌 방식으로도 생성이 가능하다.

이 어노테이션은 선언적(Declarative)인 어노테이션이다.
즉, 패키지 스캔 안에 이 어노테이션은 "이 클래스를 정의했으니 빈으로 등록하라" 는 뜻이 된다.
* ConponentScan : Component 어노테이션이 붙은 클래스들을 검색한다.추가 정보

### 01. try, finally가 뭐지?
- 메소드 내부에서 예외가 발생할 수 있는 코드 작성시 사용
- try블록에서 일어난 일과 관계없이 finally안의 구문을 실행합니다. 
- try - catch - finally를 모두 사용할 필요없이, try - finally로 사용할 수 있습니다. 하지만 try하나만 사용은 불가능합니다. 즉 try문법에서 catch와 finally는 둘 중 하나는 생략이 가능하지만, 둘 다 생략은 불가능합니다.
<br/>

https://crazykim2.tistory.com/744
https://webclub.tistory.com/71
<br/>

### 02. public Object execute(ProceedingJoinPoint joinPoint) throws Throwable 이거 예외 처리하는건가?
- 형식 : public 리턴타입 메소드명(매개변수 선언1, ...) throws 예외클래스1, 예외클래스2, ...{}
- 특징1 : throws 뒤에 Exception 만으로 모든 예외를 떠넘길 수 있다.
- 종류 : throwable, exception, error, throws
 <br/>

### 03. 매개변수와 인수
- 매개변수는 메서드에 입력으로 전달된 값을 받는 변수를 의미하고 인수는 메서드를 호출할 때 전달하는 입력값을 의미한다.
  ```
  public class Sample {
      int sum(int a, int b) {  // a, b 는 매개변수
          return a+b;
      }

      public static void main(String[] args) {
          Sample sample = new Sample();
          int c = sample.sum(3, 4);  // 3, 4는 인수

          System.out.println(c);
      }
  }
  ```
- 매개변수 - 메서드에 전달된 값을 저장하는 변수
- 인수 - 메서드에 전달하는 값
<br/>
출처 : https://wikidocs.net/225
<br/>

### 04. public TimeTraceAop timeTraceAop() { return new TimeTraceAop();}
- 스프링 빈 등록하는 TimeTraceAop()는 패키지일 뿐인데, 두 번이나 사용되는 이유가 뭐지? 
함수인지 클래스인지 헷갈리는걸로 봐서, 자바에는 함수가 없는거 같다.
그리고 내가 궁금한건, timeTraceAop()는 메서드인데, 왜 return 할 때 안 쓰이냐는거다.
메서드를 return 해야 하는거 아닌가?
<br/>

### 05. Lombok이란?
Java의 라이브러리로 반복되는 메소드를 Annotation을 사용해서 자동으로 작성해주는 라이브러리

<br/><br/>
