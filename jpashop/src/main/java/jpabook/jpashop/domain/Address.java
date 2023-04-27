package jpabook.jpashop.domain;

import lombok.Getter;

import javax.persistence.Embeddable;

@Embeddable //JPA로 어딘가에 내장될 수 있다
@Getter // Immutable 하게끔, Setter 제거
public class Address {
    //변경 불가능하게 설계해서, 사용할 때만 생성자 호출
    private String city;
    private String street;
    private String zipcode;

    protected Address() {
    }

    public Address(String city, String street, String zipcode) {
        this.city = city;
        this.street = street;
        this.zipcode = zipcode;
    }
}
