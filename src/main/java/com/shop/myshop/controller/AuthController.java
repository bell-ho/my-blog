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
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
@RestController
public class AuthController {

    private final MemberService memberService;
    private final TokenProvider tokenProvider;
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @PostMapping("/signin")
    public ResponseEntity<?> authenticate(@RequestBody LoginRequestDTO dto, HttpServletRequest request) {
        try {
            ResponseData data = new ResponseData(RequestResultEnum.SUCCESS);
            MemberResponseDTO member = new MemberResponseDTO(memberService.getByCredentials(dto.getEmail(), dto.getPassword(), passwordEncoder));

            final String token = tokenProvider.create(member.toEntity());

            member.setToken(token);

            data.getData().put("member", member);

            HttpSession session = request.getSession();
            session.setAttribute("loginMember", member);

            return ResponseEntity.ok(member);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/signup")
    public ResponseEntity<?> createMember(@RequestBody MemberInsertRequestDTO dto) {
        try {
            ResponseData data = new ResponseData(RequestResultEnum.SUCCESS);
            MemberResponseDTO member = new MemberResponseDTO(memberService.join(dto.toEntity()));

            final String token = tokenProvider.create(member.toEntity());

            member.setToken(token);

            data.getData().put("member", member);

            return ResponseEntity.ok(data);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/login")
    public ResponseEntity<?> loginInfo(@SessionAttribute(name = "loginMember", required = false) MemberResponseDTO member) {
        try {
            ResponseData data = new ResponseData(RequestResultEnum.SUCCESS);
            data.getData().put("member", member);
            return ResponseEntity.ok(data);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
