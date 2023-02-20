package com.shop.myshop.dto;

import com.shop.myshop.domain.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberResponseDTO {

    private Long id;
    private String email;
    private String token;
    private String name;
    private String nickName;

    @Builder
    public MemberResponseDTO(Member entity) {
        if (entity != null) {
            this.email = entity.getEmail() != null ? entity.getEmail() : "";
            this.name = entity.getName();
            this.nickName = entity.getNickName();
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
