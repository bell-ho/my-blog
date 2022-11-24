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
    private String name;
    private Address address;
    private String token;

    @Builder
    public MemberResponseDTO(Member entity) {
        this.id = entity.getId();
        this.name = entity.getName();
        this.email = entity.getEmail();
        this.password = entity.getPassword();
        this.address = new Address(entity.getAddress().getCity(), entity.getAddress().getStreet(), entity.getAddress().getStreet());
    }

    public Member toEntity() {
        return Member.builder()
                .id(id)
                .name(name)
                .email(email)
                .password(password)
                .address(address)
                .build();
    }
}
