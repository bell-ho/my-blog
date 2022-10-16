package com.shop.myshop.controller;

import com.shop.myshop.dto.OrderInsertRequestDTO;
import com.shop.myshop.service.ItemService;
import com.shop.myshop.service.MemberService;
import com.shop.myshop.service.OrderService;
import com.shop.myshop.utils.RequestResultEnum;
import com.shop.myshop.utils.ResponseData;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
@RestController
public class OrderController {

    private final OrderService orderService;
    private final MemberService memberService;
    private final ItemService itemService;

    @GetMapping("")
    public ResponseEntity<?> getOrders() {
        try {
            ResponseData data = new ResponseData(RequestResultEnum.SUCCESS);
            return ResponseEntity.ok(data);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("")
    public ResponseEntity<?> createOrder(@RequestBody OrderInsertRequestDTO dto) {
        try {
            ResponseData data = new ResponseData(RequestResultEnum.SUCCESS);
            orderService.order(dto.getMemberId(), dto.getItemId(), dto.getCount());
            return ResponseEntity.ok(data);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/{orderId}/cancel")
    public ResponseEntity<?> cancelOrder(@PathVariable("orderId") Long orderId) {
        try {
            ResponseData data = new ResponseData(RequestResultEnum.SUCCESS);
            orderService.cancelOrder(orderId);
            return ResponseEntity.ok(data);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
