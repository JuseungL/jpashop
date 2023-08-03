package jpabook.jpashop.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class Delivery {
    @Id
    @GeneratedValue
    @Column(name = "delivery_id")
    private Long id;

    @OneToOne(mappedBy = "delivery", fetch = FetchType.LAZY)
    private Order order;

    @Embedded
    private Address address;

    //EnumType.ORDINAL 숫자라 절대 안돼 -> READY(1), COMP(2) 사이에 ING 추가되면 이후 2로 조회하면 COMP가 아닌 ING 반환
    @Enumerated(EnumType.STRING)
    private DeliveryStatus status; //READY, COMP
}
