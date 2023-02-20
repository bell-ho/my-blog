package com.shop.myshop.domain;

import lombok.*;
import org.springframework.security.core.parameters.P;

import javax.persistence.*;

import java.util.List;

import static javax.persistence.FetchType.LAZY;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "post")
@Builder
@Getter
@Setter
@Entity
public class Post {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "post")
    private List<Images> images;

    @OneToMany(mappedBy = "post")
    private List<PostHashtagMap> postHashtagMaps;

    public static Post createPost(Member member) {
        Post post = new Post();
        post.setMember(member);
        return post;
    }
}
