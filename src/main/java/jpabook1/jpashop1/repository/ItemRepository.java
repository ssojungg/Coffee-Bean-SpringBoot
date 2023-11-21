package jpabook1.jpashop1.repository;

import jpabook1.jpashop1.domain.item.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ItemRepository {
    private final EntityManager em;

    public void save(Item item) { //처믕멩 item은 이름이 없다
        if(item.getId() == null) {
            em.persist(item); //그래서 jpa 에서 제공하는 persist를 사용할 것이다

        } else{
            em.merge(item); //그게 아니면 em.merger사용. 업데이트 비슷한 것
        }
        //item은 jpa 저장하기 전까지 아이디값이 없어서, id값이 없다는 것은 완전히 새로 생성하는 객체
        //em.persis를 통해서 신규를 등록할 것이고 이미 아이디 값이 았다? 그러면 이미 디비에 한번 등록한 녀석(가지고 온 것)

    }

    public Item findOne(Long id) {
        return em.find(Item.class, id);
    }

    public List<Item> findAll() {
        return em.createQuery("select i from Item i", Item.class)
                .getResultList();
    }
}
