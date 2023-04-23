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

# AOP 
- core concern과 cross-cutting conern을 분리할 수 있다.
- Aspect Oriented Programming
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

| 컴포넌트 ( Component )
구성요소 라는 뜻으로

컴포넌트는 독립적인 단위 모듈이다. 유저가 사용하는 시스템에 대한 조작장치를 이야기한다.

| @Component 란 
개발자가 직접 작성한 Class 를 Bean 으로 만드는 것이다.

싱글톤 클래스 빈을 생성하는 어노테이션이다. 물론 @Scope를 통해 싱글톤이 아닌 방식으로도 생성이 가능하다.

이 어노테이션은 선언적(Declarative)인 어노테이션이다.

즉, 패키지 스캔 안에 이 어노테이션은 "이 클래스를 정의했으니 빈으로 등록하라" 는 뜻이 된다.

 * ConponentScan : Component 어노테이션이 붙은 클래스들을 검색한다.추가 정보

<br/><br/>
