package jpabook1.jpashop1.domain;


import lombok.Getter;
import lombok.Setter;
import jpabook1.jpashop1.domain.Order;
import jpabook1.jpashop1.domain.Address;



import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter

public class Member {
    public static Member member;
    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;


    private String name;

    @Embedded
    private Address address;


    @OneToMany(mappedBy = "member")
    private List<Order> orders = new ArrayList<>();
}
