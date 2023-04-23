#### 작성일 : 2023/04/23, 작성자 : [wonjae124](https://github.com/wonjae124)

<br/><br/>
# AOP 
- 모든 메서드의 호출 시간 측정
- 공통 관심사항, 핵심 관심 사항
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
