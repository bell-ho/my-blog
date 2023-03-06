package com.shop.myshop.dto;

import com.shop.myshop.domain.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberResponse {

    private Long id;
    private String email;
    private String token;
    private String name;
    private String nickName;
    private String role;

    @Builder
    public MemberResponse(Member entity) {
        if (entity != null) {
            this.email = entity.getEmail() != null ? entity.getEmail() : "";
            this.name = entity.getName();
            this.nickName = entity.getNickName();
            this.role = entity.getRole().name().toLowerCase();
        }
    }

    public Member toEntity() {
        return Member.builder()
                .email(email)
                .name(name)
                .nickName(nickName)
                .build();
    }
}
