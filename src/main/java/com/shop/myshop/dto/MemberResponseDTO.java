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
    private String name;
    private Address address;

    @Builder
    public MemberResponseDTO(Member entity) {
        this.id = entity.getId();
        this.name = entity.getName();
        this.address = new Address(entity.getAddress().getCity(), entity.getAddress().getStreet(), entity.getAddress().getStreet());
    }
}
