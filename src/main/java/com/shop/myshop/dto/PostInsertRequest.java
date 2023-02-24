package com.shop.myshop.dto;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class PostInsertRequest {

    private MemberDTO member;
    private List<String> hashtags = new ArrayList<>();
    private String content;
    private List<String> images = new ArrayList<>();

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MemberDTO {
        private String uniqueKey;
    }
}
