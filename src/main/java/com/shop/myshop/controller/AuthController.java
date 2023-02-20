package com.shop.myshop.controller;

import com.shop.myshop.domain.Member;
import com.shop.myshop.dto.MemberInsertRequestDTO;
import com.shop.myshop.dto.MemberResponseDTO;
import com.shop.myshop.security.TokenProvider;
import com.shop.myshop.service.MemberService;
import com.shop.myshop.utils.RequestResultEnum;
import com.shop.myshop.utils.ResponseData;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
@RestController
public class AuthController {

    private final MemberService memberService;
    private final TokenProvider tokenProvider;

    @GetMapping("/validation-user/{key}")
    public ResponseEntity<ResponseData> validationUser(@PathVariable("key") String uniqueKey) {
        Member member = memberService.findByUniqueKey(uniqueKey);

        RequestResultEnum result = (member != null) ? RequestResultEnum.SUCCESS : RequestResultEnum.NOT_FOUND;
        ResponseData data = new ResponseData();

        if (member != null) {
            MemberResponseDTO memberRes = new MemberResponseDTO(member);
            final String token = tokenProvider.create(memberRes.toEntity());
            memberRes.setToken(token);
            data = ResponseData.fromResult(result).add("member", memberRes);
        } else {
            data = ResponseData.fromResult(result).add("member", null);
        }
        return ResponseEntity.ok(data);
    }

    @PostMapping("/signup")
    public ResponseEntity<ResponseData> createMember(@RequestBody MemberInsertRequestDTO dto) {
        MemberResponseDTO member = new MemberResponseDTO(memberService.join(dto.toEntity()));

        final String token = tokenProvider.create(member.toEntity());
        member.setToken(token);

        ResponseData data = ResponseData.fromResult(RequestResultEnum.SUCCESS).add("member", member);

        return ResponseEntity.ok(data);
    }
}
