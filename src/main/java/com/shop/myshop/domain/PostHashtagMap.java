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
public class PostHashtagMap extends BaseEntity {

    @EmbeddedId
    private PostHashtagId id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "post_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Post post;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "hashtag_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Hashtag hashtag;

    public static PostHashtagMap createPostHashtag(Post post, Hashtag hashtag) {
        PostHashtagId postHashtagId = new PostHashtagId(post.getId(), hashtag.getId());

        PostHashtagMap postHashtagMap = new PostHashtagMap();
        postHashtagMap.setId(postHashtagId);
        postHashtagMap.setPost(post);
        postHashtagMap.setHashtag(hashtag);
        return postHashtagMap;
    }
}
