package jpabook.jpashop.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Setter @Getter
public class Member {
    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String name;

    @Embedded
    private Address address;

    //연관관계 주인이 아님을 알려줌. Order테이블의 member필드에 의해 매핑 된거야. @JoinColumn(name = "member_id")와 짝
    @OneToMany(mappedBy = "member")
    //@JoinColumn(name = "member_id")
    private List<Order> orders = new ArrayList<>();
}
