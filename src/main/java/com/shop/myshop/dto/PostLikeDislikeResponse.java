package com.shop.myshop.dto;

import com.shop.myshop.domain.PostLikeDislike;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostLikeDislikeResponse {

    private Long id;
    private Long memberId;
    private Long postId;
    private String type;

    @Builder
    public PostLikeDislikeResponse(PostLikeDislike entity) {
        this.id = entity.getId();
        this.memberId = entity.getMember().getId();
        this.postId = entity.getPost().getId();
        this.type = entity.getType().name().toLowerCase();
    }
}
