package com.shop.myshop.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
public class MemberUpdateRequest {

    @NotEmpty(message = "필수 값")
    private String name;
    private String city;

}
