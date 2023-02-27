package com.shop.myshop.controller;

import com.shop.myshop.domain.Post;
import com.shop.myshop.domain.PostLikeDislike;
import com.shop.myshop.dto.PostInsertRequest;
import com.shop.myshop.dto.PostLikeDislikeResponse;
import com.shop.myshop.dto.PostResponse;
import com.shop.myshop.service.PostService;
import com.shop.myshop.utils.PageResponse;
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
    public ResponseEntity<?> getPosts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(required = false) String keyword
    ) {
        PageResponse<PostResponse> posts = postService.getPosts(page, size,keyword);

        ResponseData data = ResponseData.fromResult(RequestResultEnum.SUCCESS).add("posts", posts);
        return ResponseEntity.ok(data);
    }

    @PostMapping("")
    public ResponseEntity<?> createPost(@RequestBody PostInsertRequest dto) {

        Long postId = postService.createPost(
                        dto.getMember().getUniqueKey(),
                        dto.getContent(),
                        dto.getHashtags(),
                        dto.getImages(),
                        dto.isHide())
                .getId();

        ResponseData data = ResponseData.fromResult(RequestResultEnum.SUCCESS).add("postId", postId);
        return ResponseEntity.ok(data);
    }

    @PostMapping("/{postId}/type/{type}/member/{memberUniqueKey}")
    public ResponseEntity<?> likeDislikePost(@PathVariable Long postId, @PathVariable String memberUniqueKey, @PathVariable String type) {

        PostLikeDislikeResponse postLikeDislike = new PostLikeDislikeResponse(postService.likeDislikePost(postId, memberUniqueKey, type));

        ResponseData data = ResponseData.fromResult(RequestResultEnum.SUCCESS).add("postLikeDislike", postLikeDislike);
        return ResponseEntity.ok(data);
    }
}
