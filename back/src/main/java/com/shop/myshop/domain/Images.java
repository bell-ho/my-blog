package com.shop.myshop.domain;

import lombok.*;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@NoArgsConstructor
@AllArgsConstructor
@Table(name = "images")
@Builder
@Getter
@Setter
@Entity
public class Images extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    @Column(name = "src")
    private String src;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    public static Images createImage(Post post, String src) {
        Images images = new Images();
        images.setPost(post);
        images.setSrc(src);
        return images;
    }
}
