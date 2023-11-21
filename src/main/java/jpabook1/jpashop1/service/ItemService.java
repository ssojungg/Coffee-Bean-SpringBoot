package jpabook1.jpashop1.service;

import jpabook1.jpashop1.domain.item.Book;
import jpabook1.jpashop1.domain.item.Item;
import jpabook1.jpashop1.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.web.bind.annotation.RequestMapping;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ItemService {
    private final ItemRepository itemRepository;

    @Transactional
    public void saveItem(Item item){
        itemRepository.save(item);
    }
    /**
     * 영속성 컨텍스트가 자동 변경
     */
    @Transactional
    public void updateItem(Long itemId, String name, int price, int stockQuantity)
    {
        Item findItem = itemRepository.findOne(itemId);
        findItem.setPrice(price);
        findItem.setStockQuantity(stockQuantity);
        findItem.setName(name);
    }
    public List<Item> findItems(){
        return itemRepository.findAll();
    }

    public Item findOne(Long itemId){
        return itemRepository.findOne(itemId);
    }
}
