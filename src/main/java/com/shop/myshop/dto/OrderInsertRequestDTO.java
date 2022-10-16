package com.shop.myshop.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderInsertRequestDTO {
    private Long memberId;
    private Long itemId;
    private int count;
}
