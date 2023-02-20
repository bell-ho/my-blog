package com.shop.myshop.domain;

import lombok.*;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "post_hashtag_map")
@Builder
@Getter
@Setter
@Entity
public class PostHashtagMap {

    @EmbeddedId
    private PostHashtagId id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "post_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Post post;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "hashtag_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Hashtag hashtag;

    public static PostHashtagMap createPostHashtag(Post post, Hashtag hashtag) {
        PostHashtagMap postHashtagMap = new PostHashtagMap();
        postHashtagMap.setPost(post);
        postHashtagMap.setHashtag(hashtag);
        return postHashtagMap;
    }
}
