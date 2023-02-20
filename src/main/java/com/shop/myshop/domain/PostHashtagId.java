package com.shop.myshop.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class PostHashtagId implements Serializable {

    @Column(name="post_id")
    private Long postId;

    @Column(name="hashtag_id")
    private Long hashtagId;
}
