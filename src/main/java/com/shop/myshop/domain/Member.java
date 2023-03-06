package com.shop.myshop.domain;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Table(name = "member", uniqueConstraints = {@UniqueConstraint(columnNames = "email")})
@Builder
@Getter
@Setter
@Entity
public class Member extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "nick_name")
    private String nickName;
    @Column(name = "unique_key")
    private String uniqueKey;
    @Column(name = "email")
    private String email;
    @Column(name = "provider")
    private String provider;
    @OneToMany(mappedBy = "member")
    private List<Post> posts;
    @Enumerated(EnumType.STRING)
    private RoleType role;
}
