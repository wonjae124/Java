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

<img src="https://blog.kakaocdn.net/dn/bbpQ34/btrsaR2tf9k/kgLWHjGXjWBNkdmIp0kawk/img.png](https://github.com/wonjae124/Java/blob/main/image/img.png">

<br/><br/>
