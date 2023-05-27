package jpabook.jpashop.domain;

import jpabook.jpashop.domain.item.Item;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;


@Entity
@Getter @Setter
public class OrderItem {
    @Id @GeneratedValue
    @Column(name = "order_item_id")
    private Long id;

    @ManyToOne(fetch= LAZY)
    @JoinColumn(name = "item_id") // 연관관계 주인이다
    private Item item;

    @ManyToOne(fetch= LAZY) // 다수의 오더아이템은 하나의 오더만 가질 수 있다.
    @JoinColumn(name = "order_id")
    private Order order;
   private int orderPrice; // 주문 가격
   private int count; // 주문 수량

    //== 생성 메서드 ==//
    public static OrderItem createOrderItem(Item item, int orderPrice, int count) {
        OrderItem orderItem = new OrderItem();
        orderItem.setItem(item);
        orderItem.setOrderPrice(orderPrice);
        orderItem.setCount(count);
        item.removeStock(count); // 재고에서 감산
        return orderItem;
    }
    //== 비즈니스 로직 ==//
    // getItem을 쓸 수 있는 이유는 @Getter를 통해서 Item 필드를 사용할 수 있기 떄문이다.
    // -3된 재고 수량을, 다시 +3으로 원상복구(Item 클래스에 있는 addStock 메소드)

    public void cancel(){
        getItem().addStock(count);
    }

    //==조회 로직==//
    public int getTotalPrice() {
        return getOrderPrice() * getCount();
    }
}
