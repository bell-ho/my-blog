package com.shop.myshop.service;

import com.shop.myshop.domain.Post;
import com.shop.myshop.domain.PostLikeDislike;

import java.util.List;

public interface PostService {
    Post createPost(String memberUniqueKey, String content, List<String> hashtags,List<String> images);

    List<Post> getPosts();

    PostLikeDislike likeDislikePost(Long postId, String memberUniqueKey,String type);
}
