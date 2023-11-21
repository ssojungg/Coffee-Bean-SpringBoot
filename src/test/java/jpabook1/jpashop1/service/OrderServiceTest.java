package jpabook1.jpashop1.service;

import jpabook1.jpashop1.domain.Address;
import jpabook1.jpashop1.domain.Member;
import jpabook1.jpashop1.domain.Order;
import jpabook1.jpashop1.domain.OrderStatus;
import jpabook1.jpashop1.domain.item.Book;
import jpabook1.jpashop1.domain.item.Item;
import jpabook1.jpashop1.exception.NotEnoughStockException;
import jpabook1.jpashop1.repository.OrderRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import javax.persistence.EntityManager;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class OrderServiceTest {
    @Autowired
    EntityManager em;
    @Autowired
    OrderService orderService;
    @Autowired
    OrderRepository orderRepository;

    @Test
    public void 상품주문() throws Exception {
        //Given

        Member member = CreateMember();

        Book book = CreateBook("시골 JPA", 10000, 10);
        int orderCount = 2;
        //When
        Long orderId = orderService.order(member.getId(), book.getId(), orderCount);


        //Then
        Order getOrder = orderRepository.findOne(orderId);

        assertEquals(OrderStatus.ORDER, getOrder.getStatus(), "상품 주문시 상태는 order");
        assertEquals(1, getOrder.getOrderItems().size(), "주문한 상품 종류 수가 정확해야 한다.");
        assertEquals(10000 * orderCount, getOrder.getTotalPrice(), "주문 가격은 가격 * 수량이다");
        assertEquals(8, book.getStockQuantity(), "주문 수량만큼 재고가 줄어야 한다");
    }

    @Test
    public void 상품주문_재고수량초과() throws Exception {
        //given
        Member member = CreateMember();
        Item item = CreateBook("시골 JPA", 10000, 10);

        int orderCount = 11;

        assertThrows(NotEnoughStockException.class, () -> {
            orderService.order(member.getId(), item.getId(), orderCount);
        }, "재고 수량 부족 예외가 발생해야 한다");
    }

    @Test
    public void 주문최소() throws Exception {
        //given
        Member member = CreateMember();
        Book item = CreateBook("시골 JPA",10000,10);

        int orderCount = 2;

        Long orderId = orderService.order(member.getId(), item.getId(), orderCount);
        //when
        orderService.cancelOrder(orderId);

        //then
        Order getOrder = orderRepository.findOne(orderId);
        assertAll(
                () -> assertEquals(OrderStatus.CANCEL, getOrder.getStatus(), "주문 취소시 상태는 CANCEL 이다"),
                () -> assertEquals(10, item.getStockQuantity(), "주문이 취소된 상품은 그만큼 재고가 증가해야 한다")
        );
    }

    private Book CreateBook(String name, int stockQuantity, int price) {
        Book book = new Book();
        book.setName(name);
        book.setStockQuantity(price);
        book.setPrice(stockQuantity);
        em.persist(book);
        return book;
    }

    private Member CreateMember() {
        Member member = new Member();
        member.setName("회원1");
        member.setAddress(new Address("서울", "강가", "123-123"));
        em.persist(member);
        em.persist(member);
        return member;
    }

}