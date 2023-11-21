package jpabook1.jpashop1.domain.item;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
@Entity
@Getter @Setter
@DiscriminatorValue("B")
public class Book extends Item{

    public String  author;
    public String isbn;


}
