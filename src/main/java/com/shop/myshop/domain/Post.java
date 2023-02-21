package com.shop.myshop.domain;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.LAZY;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "post")
@Builder
@Getter
@Setter
@Entity
public class Post extends BaseEntity {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    @Column(name = "content")
    private String content;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Builder.Default
    @OneToMany(mappedBy = "post")
    private List<Images> images = new ArrayList<>();
    @Builder.Default
    @OneToMany(mappedBy = "post")
    private List<PostHashtagMap> postHashtagMaps = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "post")
    private List<PostLikeDislike> postLikeDislikes = new ArrayList<>();

    public static Post createPost(Member member,String content) {
        Post post = new Post();
        post.setMember(member);
        post.setContent(content);
        return post;
    }
}
