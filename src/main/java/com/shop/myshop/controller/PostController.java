package com.shop.myshop.controller;

import com.shop.myshop.domain.Post;
import com.shop.myshop.dto.PostInsertRequest;
import com.shop.myshop.service.PostService;
import com.shop.myshop.utils.RequestResultEnum;
import com.shop.myshop.utils.ResponseData;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/v1/post")
@RestController
public class PostController {
    private final PostService postService;

    @PostMapping("")
    public ResponseEntity<?> createPost(@RequestBody PostInsertRequest dto) {

        Post post = postService.createPost(dto.getMember().getId(), dto.getHashtags());

        ResponseData data = ResponseData.fromResult(RequestResultEnum.SUCCESS).add("post", post);
        return ResponseEntity.ok(data);
    }
}
