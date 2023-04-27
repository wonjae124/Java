package jpabook.jpashop.domain;

import jpabook.jpashop.domain.item.Item;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter @Setter
public class Category {
    @Id @GeneratedValue
    @Column(name="category_id")
    private Long id;

    private String name;

    @ManyToMany
    @JoinTable(name = "category_item",
            joinColumns = @JoinColumn(name = "category_id"), // 현재 엔티티를 참조하는 외래키
            inverseJoinColumns = @JoinColumn(name = "item_id") // 반대방향 엔티티를 참조하는 외래키
    ) // 다대다를 일대다로 풀어주는 테이블
    private List<Item> items = new ArrayList<>();

    @ManyToOne(fetch= LAZY)
    @JoinColumn(name = "parent_id")
    private Category parent;
    
    @OneToMany(mappedBy = "parent")
    private List<Category> child = new ArrayList<>(); // child 는     여러개 나올 수 있는 리스트

    // 연관관계 편의 메소드
    public void addChildCategory(Category child) { // 자기 자신 받아온다(양쪽에 들어오게 한다)
        this.child.add(child); // 자식도 설정
        child.setParent(this); // 부모도 설정
    }
}
