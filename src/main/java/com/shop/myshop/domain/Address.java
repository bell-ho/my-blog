package com.shop.myshop.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Embeddable;

@Embeddable // 내장 가능
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Address {

    private String city;
    private String zipcode;
    private String street;
}
