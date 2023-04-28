package jpabook.jpashop.domain;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Member {
    @Id @GeneratedValue
    @Column(name = "member_id") // 매핑에서 보면 pk가 member_id 이다. 설정 안해주면 아랫 줄의 id로 설정된다.
    private Long id;

    private String name;

    @Embedded
    private Address address;
    // 연관관계의 주인이 아니에요. 거울이에요
    // 오더 테이블에 있는 member 필드와 매핑 된거다

    @OneToMany(mappedBy = "member")
    private List<Order> orders = new ArrayList<>();
}
