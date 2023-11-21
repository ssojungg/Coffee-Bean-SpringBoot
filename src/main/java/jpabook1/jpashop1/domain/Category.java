package jpabook1.jpashop1.domain;

import jpabook1.jpashop1.domain.item.Item;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ManyToAny;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Category {

    @Id @GeneratedValue
    @Column(name = "Category_id")

    private Long id;

    private String name;

    @ManyToMany
    @JoinTable(name = "category_item",
            joinColumns = @JoinColumn(name = "categoty_id"),
            inverseJoinColumns = @JoinColumn(name = "item_id"))//조인 테이블 필요한 이유, 중간 테이블 매핑 해주기 위해 필요
            //중간 테이블 매핑 해줘야함. 객체는 다대다 관계 가능. 관계형 디비는 양쪽에 가지고 있지 않기 때문에 필요함.

    //핑드 추가 불가. 실무에서는 못씀


    private List<Item> items = new ArrayList<>();


    @ManyToOne(fetch = FetchType.LAZY) //내 부모니까
    @JoinColumn(name = "parent_id")
    private Category parent;

    @OneToMany(mappedBy = "parent") //자식은 여러개 가질 수 있으니까
    private List<Category> child = new ArrayList<>();

    public void addChildCategory(Category child) {
        this.child.add(child);
        child.setParent(this);
    }
}
