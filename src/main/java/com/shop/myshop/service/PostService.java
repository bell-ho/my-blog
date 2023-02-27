package com.shop.myshop.service;

import com.shop.myshop.domain.Post;
import com.shop.myshop.domain.PostLikeDislike;
import com.shop.myshop.dto.PostResponse;
import com.shop.myshop.utils.PageResponse;

import java.util.List;

public interface PostService {
    Post createPost(String memberUniqueKey, String content, List<String> hashtags,List<String> images,boolean isHide);

    PageResponse<PostResponse> getPosts(int page, int size,String keyword);

    PostLikeDislike likeDislikePost(Long postId, String memberUniqueKey,String type);

}
