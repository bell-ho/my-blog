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

//    @PostMapping("/signin")
//    public ResponseEntity<?> authenticate(@RequestBody LoginRequestDTO dto, HttpServletRequest request) {
//        try {
//            MemberResponseDTO member = new MemberResponseDTO();
//            final String token = tokenProvider.create(member.toEntity());
//            member.setToken(token);
//
//            ResponseData data = ResponseData.fromResult(RequestResultEnum.SUCCESS)
//                    .add("member", member);
//
//            HttpSession session = request.getSession();
//            session.setAttribute("loginMember", member);
//
//            return ResponseEntity.ok(data);
//        } catch (Exception e) {
//            ResponseData error = ResponseData.fromException(e);
//            return ResponseEntity.status(error.getStatus()).body(e);
//        }
//    }

    @GetMapping("/validation-user/{key}")
    public ResponseEntity<ResponseData> validationUser(@PathVariable("key") String uniqueKey) {
        Member member = memberService.findByUniqueKey(uniqueKey);
        RequestResultEnum result = (member != null) ? RequestResultEnum.SUCCESS : RequestResultEnum.NOT_FOUND;

        ResponseData data = ResponseData.fromResult(result).add("member", member);
        return ResponseEntity.ok(data);
    }

//    @PostMapping("/signup")
//    public ResponseEntity<?> createMember(@RequestBody MemberInsertRequestDTO dto) {
//        try {
//            ResponseData data = new ResponseData(RequestResultEnum.SUCCESS);
//            MemberResponseDTO member = new MemberResponseDTO(memberService.join(dto.toEntity()));
//
//            final String token = tokenProvider.create(member.toEntity());
//
//            member.setToken(token);
//
//            data.getData().put("member", member);
//
//            return ResponseEntity.ok(data);
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }

    @PostMapping("/signup")
    public ResponseEntity<ResponseData> createMember(@RequestBody MemberInsertRequestDTO dto) {
        MemberResponseDTO member = new MemberResponseDTO(memberService.join(dto.toEntity()));

        final String token = tokenProvider.create(member.toEntity());
        member.setToken(token);

        ResponseData data = ResponseData.fromResult(RequestResultEnum.SUCCESS).add("member", member);

        return ResponseEntity.ok(data);
    }

    @GetMapping("/login")
    public ResponseEntity<?> loginInfo(@SessionAttribute(name = "loginMember", required = false) MemberResponseDTO member) {

        try {
            ResponseData data = ResponseData.fromResult(RequestResultEnum.SUCCESS)
                    .add("member", member);

            return ResponseEntity.ok(data);
        } catch (Exception e) {
            ResponseData error = ResponseData.fromException(e);
            return ResponseEntity.status(error.getStatus()).body(e);
        }
    }
}
