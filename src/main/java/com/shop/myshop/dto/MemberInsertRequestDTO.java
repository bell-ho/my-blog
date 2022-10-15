package com.shop.myshop.dto;

import com.shop.myshop.domain.Address;
import com.shop.myshop.domain.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class MemberInsertRequestDTO {

    @NotEmpty(message = "필수 값")
    private String name;
    private String city;
    private String street;
    private String zipcode;

    public Member toEntity() {
        return Member.builder()
                .name(name)
                .address(new Address(city, street, zipcode))
                .build();
    }
}
