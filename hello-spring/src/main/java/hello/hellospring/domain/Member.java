package hello.hellospring.domain;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

// 아래는 JPA에서 관리하는 entity다!
@Entity
public class Member {

    // Identity 방식, db에 value를 넣어주면 알아서, id(PK)를 입력해주는 정책
    @Id    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 식별자 ID, 시스템이 정한 I

    // db의 컬럼명이 username이면 이렇게 매핑하기, => @Column(name = "username")
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
