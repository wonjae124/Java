package jpabook.jpashop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders") // 테이블명, 아래 Order 클래스와 구분하기 위해 지정
@Getter @Setter
public class Order {

    @Id @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    // 연관관계의 주인을 정해주는 것임, 어디에 값이 변경됬을 떄 포렌키를 바꿔줘야한다.
    // 테이블을 보면, Orders테이블은 Member_ID를 가지고 있다
    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    // 하나의 오더는 여러 개의 아이템을 가질 수 있다
    @OneToMany(mappedBy="order") // OneToMay에서, 내가 연관관계 주인이 아니므로, mapedyBy 쓴다.
    private List<OrderItem> orderItems = new ArrayList<>();

    @OneToOne // 1대1 관계에서는 FK를 어디에 두어도 상관없다.
    @JoinColumn(name = "delivery_id") // 연관관계 주인이다.
    private Delivery delivery;
    private LocalDateTime orderDate; // 주문시간

    @Enumerated(EnumType.STRING)
    private OrderStatus status; // 주문 상태 [ORDER, CANCEL]
}