package jpabook.jpashop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.FetchType.LAZY;

@Entity
@Table(name = "orders") // 테이블명, 아래 Order 클래스와 구분하기 위해 지정
@Getter @Setter
public class Order {

    @Id @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    // 연관관계의 주인을 정해주는 것임, 어디에 값이 변경됬을 떄 포렌키를 바꿔줘야한다.
    // 테이블을 보면, Orders테이블은 Member_ID를 가지고 있다
    @ManyToOne(fetch= LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    // 하나의 오더는 여러 개의 아이템을 가질 수 있다
    // OneToMay에서, 내가 연관관계 주인이 아니므로, mapedyBy 쓴다.
    // Cascade는 persist를 전파해준다.
    @OneToMany(mappedBy="order", cascade = ALL)
    private List<OrderItem> orderItems = new ArrayList<>();

    @OneToOne(fetch= LAZY, cascade = ALL) // 1대1 관계에서는 FK를 어디에 두어도 상관없다.
    @JoinColumn(name = "delivery_id") // 연관관계 주인이다.
    private Delivery delivery;
    private LocalDateTime orderDate; // 주문시간

    @Enumerated(EnumType.STRING)
    private OrderStatus status; // 주문 상태 [ORDER, CANCEL]

    // 연관관계 메서드
    public void setMember(Member member) {
        this.member = member;
        member.getOrders().add(this);
    }

    public void addOrderItem(OrderItem orderItem){
        orderItems.add(orderItem);
        orderItem.setOrder(this);
    }

    public void setDelivery(Delivery delivery){
        this.delivery = delivery;
        delivery.setOrder(this);
    }

    //== 주문 생성 메소드==//
    public static Order createOrder(Member member, Delivery delivery, OrderItem... orderItems) {
        Order order = new Order();
        order.setMember(member);
        order.setDelivery(delivery);
        for (OrderItem orderItem : orderItems) {
            order.addOrderItem(orderItem);
        }
        order.setStatus(OrderStatus.ORDER);
        order.setOrderDate(LocalDateTime.now());
        return order;
    }

    //== 비즈니스 로직 ==//
    /**
     * 주문 취소
     */
    public void cancel() {
        if (delivery.getStatus() == DeliveryStatus.COMP){
            throw new IllegalStateException("이미 배송완료된 상품은 취소가 불가능합니다.");
        }
        this.setStatus(OrderStatus.CANCEL);
        // for문 이유 : 아이템 A, B를 주문한 건에 대해서는, 반복하면서 A랑 B로 접근해서 취소해야 한다.
        for (OrderItem orderItem : orderItems) {
            orderItem.cancel();
        }
    }

    //==조회 로직==//

    /**
     * 전체 주문 가격 조회
     */
    public int getTotalPrice() {
        return orderItems.stream()
                .mapToInt(OrderItem::getTotalPrice) // 결과값의 int를 전부 stream으로 모은다.
                .sum();
    }
}