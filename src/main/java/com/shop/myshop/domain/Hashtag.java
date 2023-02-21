package com.shop.myshop.domain;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Table(name = "hashtag")
@Builder
@Getter
@Setter
@Entity
public class Hashtag extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "hashtag")
    private List<PostHashtagMap> postHashtagMaps;

    public static Hashtag createHashtag(String name) {
        Hashtag hashtag = new Hashtag();
        hashtag.setName(name);
        return hashtag;
    }
}
