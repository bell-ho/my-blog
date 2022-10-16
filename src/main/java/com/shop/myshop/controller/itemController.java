package com.shop.myshop.controller;

import com.shop.myshop.domain.Book;
import com.shop.myshop.dto.BookInsertRequestDTO;
import com.shop.myshop.dto.BookUpdateRequestDTO;
import com.shop.myshop.dto.ItemResponseDTO;
import com.shop.myshop.service.ItemService;
import com.shop.myshop.utils.RequestResultEnum;
import com.shop.myshop.utils.ResponseData;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RequestMapping("/api/v1/items")
@RequiredArgsConstructor
@RestController
public class itemController {

    private final ItemService itemService;

    @GetMapping("")
    public ResponseEntity<?> getItems() {
        try {
            ResponseData data = new ResponseData(RequestResultEnum.SUCCESS);
            List<ItemResponseDTO> list = itemService.findItems().stream().map(ItemResponseDTO::new).collect(Collectors.toList());
            data.getData().put("list", list);
            return ResponseEntity.ok(data);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    @PostMapping("")
    public ResponseEntity<?> createItem(@RequestBody BookInsertRequestDTO dto) {
        try {
            ResponseData data = new ResponseData(RequestResultEnum.SUCCESS);

            Book book = new Book();
            book.setName(dto.getName());
            book.setPrice(dto.getPrice());
            book.setStockQuantity(dto.getStockQuantity());
            book.setAuthor(dto.getAuthor());
            book.setIsbn(dto.getIsbn());

            itemService.saveItem(book);
            return ResponseEntity.ok(data);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateItem(@PathVariable("id") Long id, @RequestBody BookUpdateRequestDTO dto) {
        try {
            ResponseData data = new ResponseData(RequestResultEnum.SUCCESS);
            itemService.updateItem(id, dto.getName(), dto.getPrice(), dto.getStockQuantity());
            return ResponseEntity.ok(data);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
