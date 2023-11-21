package jpabook1.jpashop1.domain.item;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@Getter @Setter
@DiscriminatorValue("A")
public class Album extends Item{
    private String arstist;
    private String etc;
}
