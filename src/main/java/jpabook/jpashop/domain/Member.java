package jpabook.jpashop.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
//엔티티들이 JPA를 통해 DB에 매핑된다.
@Entity
@Setter @Getter
public class Member {
    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String name;

    @Embedded
    private Address address;


    //@JsonIgnoreAPI JSON나갈때 해당 필드는 안나감 근데 여러 API에서 쓸때는 문제가 되니까 엔티티에다가 쓰면 안됨
    //연관관계 주인이 아님을 알려줌. Order테이블의 member필드에 의해 매핑 된거야. @JoinColumn(name = "member_id")와 짝
    @JsonIgnore
    @OneToMany(mappedBy = "member")
    private List<Order> orders = new ArrayList<>();
}
