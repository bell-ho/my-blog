package com.shop.myshop.controller;

import com.shop.myshop.domain.Post;
import com.shop.myshop.dto.PostInsertRequest;
import com.shop.myshop.dto.PostResponse;
import com.shop.myshop.service.PostService;
import com.shop.myshop.utils.RequestResultEnum;
import com.shop.myshop.utils.ResponseData;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RequestMapping("/api/v1/post")
@RestController
public class PostController {
    private final PostService postService;

    @GetMapping("")
    public ResponseEntity<?> getPosts() {
        List<PostResponse> posts = postService.getPosts().stream().map(PostResponse::new).collect(Collectors.toList());
        ResponseData data = ResponseData.fromResult(RequestResultEnum.SUCCESS).add("posts", posts);
        return ResponseEntity.ok(data);
    }

    @PostMapping("")
    public ResponseEntity<?> createPost(@RequestBody PostInsertRequest dto) {

        Long postId = postService.createPost(dto.getMember().getUniqueKey(), dto.getContent(), dto.getHashtags()).getId();

        ResponseData data = ResponseData.fromResult(RequestResultEnum.SUCCESS).add("postId", postId);
        return ResponseEntity.ok(data);
    }
}
