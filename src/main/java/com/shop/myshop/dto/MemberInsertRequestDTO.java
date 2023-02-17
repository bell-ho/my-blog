package com.shop.myshop.dto;

import com.shop.myshop.domain.Member;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class MemberInsertRequestDTO {


    private String name;
    private String nickName;
    private String uniqueKey;
    private String email;
    private String provider;

    public Member toEntity() {
        return Member.builder()
                .name(name)
                .nickName(nickName)
                .uniqueKey(uniqueKey)
                .email(email)
                .provider(provider)
                .build();
    }
}
