package com.shop.myshop.controller;

import com.shop.myshop.utils.RequestResultEnum;
import com.shop.myshop.utils.ResponseData;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/v1/home")
@RestController
public class HelloController {

    @GetMapping("")
    public ResponseEntity<?> hello() {
        try {
            ResponseData data = new ResponseData(RequestResultEnum.SUCCESS);
            data.getData().put("result", "성공");

            return ResponseEntity.ok(data);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
