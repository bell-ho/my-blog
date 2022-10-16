package com.shop.myshop.service;

import com.shop.myshop.domain.Item;
import com.shop.myshop.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;

    @Override
    @Transactional
    public void saveItem(Item item) {
        itemRepository.save(item);
    }

    @Override
    public Item findOne(Long id) {
        return itemRepository.findById(id).get();
    }

    @Override
    public List<Item> findItems() {
        return itemRepository.findAll();
    }

    @Override
    @Transactional
    public void updateItem(Long id, String name, int price, int stockQuantity) {
        Item findItem = itemRepository.findById(id).get();
        findItem.setName(name);
        findItem.setPrice(price);
        findItem.setStockQuantity(stockQuantity);
    }
}
