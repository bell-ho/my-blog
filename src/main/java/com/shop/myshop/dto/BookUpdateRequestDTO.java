package com.shop.myshop.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookUpdateRequestDTO {

    private String name;
    private int price;
    private int stockQuantity;
    private String author;
    private String isbn;
}
