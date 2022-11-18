package com.shop.myshop.service;

import com.shop.myshop.domain.Order;

import java.util.List;

public interface OrderService {

    List<Order> orderLists();
    Long order(Long memberId, Long itemId, int count);

    void cancelOrder(Long orderId);
}
