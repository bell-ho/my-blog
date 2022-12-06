package com.shop.myshop.dto;

import com.shop.myshop.domain.Address;
import com.shop.myshop.domain.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberResponseDTO {

    private Long id;
    private String email;
    private String password;
    private String token;

    @Builder
    public MemberResponseDTO(Member entity) {
        this.id = entity.getId();
        this.email = entity.getEmail();
        this.password = entity.getPassword();
    }

    public Member toEntity() {
        return Member.builder()
                .id(id)
                .email(email)
                .password(password)
                .build();
    }
}
