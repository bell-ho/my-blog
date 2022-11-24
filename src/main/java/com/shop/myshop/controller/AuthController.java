package com.shop.myshop.controller;

import com.shop.myshop.domain.Member;
import com.shop.myshop.dto.LoginRequestDTO;
import com.shop.myshop.dto.MemberInsertRequestDTO;
import com.shop.myshop.dto.MemberResponseDTO;
import com.shop.myshop.security.TokenProvider;
import com.shop.myshop.service.MemberService;
import com.shop.myshop.utils.RequestResultEnum;
import com.shop.myshop.utils.ResponseData;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
@RestController
public class AuthController {

    private final MemberService memberService;
    private final TokenProvider tokenProvider;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticate(@RequestBody LoginRequestDTO dto) {
        try {
            ResponseData data = new ResponseData(RequestResultEnum.SUCCESS);
            MemberResponseDTO member = new MemberResponseDTO(memberService.getByCredentials(dto.getEmail(), dto.getPassword()));

            final String token = tokenProvider.create(member.toEntity());

            member.setToken(token);

            data.getData().put("member", member);

            return ResponseEntity.ok(member);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
