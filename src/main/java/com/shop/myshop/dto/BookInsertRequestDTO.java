package com.shop.myshop.dto;

import com.shop.myshop.domain.Book;
import com.shop.myshop.domain.Item;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookInsertRequestDTO {

    private String name;
    private int price;
    private int stockQuantity;

    private String author;
    private String isbn;

    public Book toEntity() {
        return Book.builder()
                .author(author)
                .isbn(isbn)
                .build();
    }
}
