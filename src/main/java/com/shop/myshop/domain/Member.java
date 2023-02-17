package com.shop.myshop.domain;

import lombok.*;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Table(name = "member", uniqueConstraints = {@UniqueConstraint(columnNames = "email")})
@Builder
@Getter
@Setter
@Entity
public class Member {

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

}
