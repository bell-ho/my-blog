package com.shop.myshop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;

@Embeddable // 내장 가능
@Getter
@Setter
public class Address {
    private String city;
    private String zipcode;
    private String street;
}
