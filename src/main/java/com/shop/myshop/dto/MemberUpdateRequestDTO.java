package com.shop.myshop.dto;

import com.shop.myshop.domain.Address;
import com.shop.myshop.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
public class MemberUpdateRequestDTO {

    @NotEmpty(message = "필수 값")
    private String name;
    private String city;

}
