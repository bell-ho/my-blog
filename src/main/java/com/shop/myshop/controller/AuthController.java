package com.shop.myshop.controller;

import com.shop.myshop.dto.LoginRequestDTO;
import com.shop.myshop.dto.MemberInsertRequestDTO;
import com.shop.myshop.dto.MemberResponseDTO;
import com.shop.myshop.security.TokenProvider;
import com.shop.myshop.service.MemberService;
import com.shop.myshop.utils.RequestResultEnum;
import com.shop.myshop.utils.ResponseData;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
@RestController
public class AuthController {

    private final MemberService memberService;
    private final TokenProvider tokenProvider;
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @PostMapping("/signin")
    public ResponseEntity<?> authenticate(@RequestBody LoginRequestDTO dto) {
        try {
            ResponseData data = new ResponseData(RequestResultEnum.SUCCESS);
            MemberResponseDTO member = new MemberResponseDTO(memberService.getByCredentials(dto.getEmail(), dto.getPassword(), passwordEncoder));

            final String token = tokenProvider.create(member.toEntity());

            member.setToken(token);

            data.getData().put("member", member);

            return ResponseEntity.ok(member);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/signup")
    public ResponseEntity<?> createMember(@RequestBody MemberInsertRequestDTO dto) {
        try {
            ResponseData data = new ResponseData(RequestResultEnum.SUCCESS);
            memberService.join(dto.toEntity());
            return ResponseEntity.ok(data);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
