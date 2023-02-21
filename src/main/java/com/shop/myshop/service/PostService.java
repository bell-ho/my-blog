package com.shop.myshop.service;

import com.shop.myshop.domain.Post;

import java.util.List;

public interface PostService {
    Post createPost(String memberUniqueKey, String content, List<String> hashtags);

    List<Post> getPosts();
}
