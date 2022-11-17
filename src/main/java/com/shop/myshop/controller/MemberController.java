package com.shop.myshop.controller;

import com.shop.myshop.domain.Member;
import com.shop.myshop.dto.MemberInsertRequestDTO;
import com.shop.myshop.dto.MemberResponseDTO;
import com.shop.myshop.dto.MemberUpdateRequestDTO;
import com.shop.myshop.service.MemberService;
import com.shop.myshop.utils.RequestResultEnum;
import com.shop.myshop.utils.ResponseData;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RequestMapping("/api/v1/members")
@RestController
public class MemberController {

    private final MemberService memberService;

    @GetMapping("")
    public ResponseEntity<?> getMembers() {
        try {
            ResponseData data = new ResponseData(RequestResultEnum.SUCCESS);
            List<MemberResponseDTO> members = memberService.findMembers().stream().map(MemberResponseDTO::new).collect(Collectors.toList());
            data.getData().put("list", members);

//            data.getData().put("list", new Result<>(members.size(), members));

            return ResponseEntity.ok(data);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Data
    @AllArgsConstructor
    static class Result<T> {
        private int count;
        private T data;
    }

    @PostMapping("")
    public ResponseEntity<?> createMember(@RequestBody @Valid MemberInsertRequestDTO dto) {
        try {
            ResponseData data = new ResponseData(RequestResultEnum.SUCCESS);
            memberService.join(dto.toEntity());
            return ResponseEntity.ok(data);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateMember(@PathVariable("id") Long id, @RequestBody @Valid MemberUpdateRequestDTO dto) {
        try {
            ResponseData data = new ResponseData(RequestResultEnum.SUCCESS);
            memberService.update(id, dto.getName());
            return ResponseEntity.ok(data);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
