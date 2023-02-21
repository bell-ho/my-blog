package com.shop.myshop.domain;

import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@NoArgsConstructor
public class PostHashtagId implements Serializable {

    @Column(name="post_id")
    private Long postId;

    @Column(name="hashtag_id")
    private Long hashtagId;

    public PostHashtagId(Long postId, Long hashtagId) {
        this.postId = postId;
        this.hashtagId = hashtagId;
    }
}
