package com.shop.myshop.service;

import com.shop.myshop.domain.Item;

import java.util.List;

public interface ItemService {

    void saveItem(Item item);

    Item findOne(Long id);

    List<Item> findItems();
}
