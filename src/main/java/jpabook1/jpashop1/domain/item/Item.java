package jpabook1.jpashop1.domain.item;

import jpabook1.jpashop1.domain.Category;
import jpabook1.jpashop1.exception.NotEnoughStockException;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Getter @Setter
public abstract class Item {
    @Id
    @GeneratedValue
    @Column(name = "item_id")
    private Long id;

    private String name;
    private int price;
    private int stockQuantity;

    @ManyToMany(mappedBy = "items")
    private List<Category> categories = new ArrayList<>();

    //비지니스 로직//

    public void addStock(int quantity) {
        //stock 증가
        this.stockQuantity += quantity;
    }

        //stock 감소
    public void removeStockS(int quantity) {
        int restStock = this.stockQuantity - quantity;
        if(restStock < 0) {
            throw new NotEnoughStockException("need more stock");
        }
        this.stockQuantity = restStock;
    }

    }

