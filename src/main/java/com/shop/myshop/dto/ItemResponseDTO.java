package com.shop.myshop.dto;

import com.shop.myshop.domain.Item;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemResponseDTO {

    private Long id;
    private String name;
    private int price;
    private int stockQuantity;

    @Builder
    public ItemResponseDTO(Item entity) {
        this.id = entity.getId();
        this.name = entity.getName();
        this.price = entity.getPrice();
        this.stockQuantity = entity.getStockQuantity();
    }
}
