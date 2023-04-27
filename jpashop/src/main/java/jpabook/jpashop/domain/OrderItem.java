package jpabook.jpashop.domain;

import jpabook.jpashop.domain.item.Item;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
public class OrderItem {
    @Id @GeneratedValue
    @Column(name = "order_item_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "item_id") // 연관관계 주인이다
    private Item item;

    @ManyToOne // 다수의 오더아이템은 하나의 오더만 가질 수 있다.
    @JoinColumn(name = "order_id")
    private Order order;
   private int orderPrice; // 주문 가격
   private int count; // 주문 수량

}
