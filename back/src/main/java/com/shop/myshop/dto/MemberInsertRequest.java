package com.shop.myshop.dto;

import com.shop.myshop.domain.LikeDislikeType;
import com.shop.myshop.domain.Member;
import com.shop.myshop.domain.RoleType;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class MemberInsertRequest {

    private String name;
    private String nickName;
    private String uniqueKey;
    private String email;
    private String provider;
    private String role;

    public Member toEntity() {
        return Member.builder()
                .name(name)
                .nickName(nickName)
                .uniqueKey(uniqueKey)
                .email(email)
                .provider(provider)
                .role(RoleType.ROLE_USER)
                .build();
    }
}
