package com.shop.myshop.service;

import com.shop.myshop.domain.*;
import com.shop.myshop.repository.ItemRepository;
import com.shop.myshop.repository.MemberRepository;
import com.shop.myshop.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;

    @Override
    public List<Order> orderLists() {
        return orderRepository.findAll();
    }

    /**
     * 주문
     */
    @Override
    @Transactional
    public Long order(Long memberId, Long itemId, int count) {
        Member member = memberRepository.findById(memberId).get();
        Item item = itemRepository.findById(itemId).get();

        // 배송 정보 생성
        Delivery delivery = new Delivery();
        delivery.setAddress(member.getAddress());

        // 주문 상품 생성
        OrderItem orderItem = OrderItem.createOrderItem(item, item.getPrice(), count);

        // 주문 생성
        // cascade = CascadeType.ALL 옵션 때문에 delivery,orderItem 을 따로 save 안해줘도됨
        // cascade = CascadeType.ALL 을 쓰기 전에는 테이블 관계를 잘 살펴서 해야함 (하나만 참조할때 쓰면 좋음)
        Order order = Order.createOrder(member, delivery, orderItem);

        // 주문 저장
        return orderRepository.save(order).getId();
    }

    /**
     * 주문 취소
     */
    @Override
    @Transactional
    public void cancelOrder(Long orderId) {
        Order order = orderRepository.findById(orderId).get();
        order.cancel();
    }

//    public List<Order> findOrders(OrderSearch orderSearch) {
//
//    }
}
