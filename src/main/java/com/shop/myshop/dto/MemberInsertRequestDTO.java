package com.shop.myshop.dto;

import com.shop.myshop.domain.Address;
import com.shop.myshop.domain.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class MemberInsertRequestDTO {

    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    @NotEmpty(message = "필수 값")
    private String name;
    private String email;
    private String password;
    private String city;
    private String street;
    private String zipcode;

    public Member toEntity() {
        return Member.builder()
                .name(name)
                .email(email)
                .password(passwordEncoder.encode(password))
                .address(new Address(city, street, zipcode))
                .build();
    }
}
