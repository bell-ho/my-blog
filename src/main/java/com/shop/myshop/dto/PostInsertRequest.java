package com.shop.myshop.dto;

import com.shop.myshop.domain.Hashtag;
import com.shop.myshop.domain.Member;
import com.shop.myshop.domain.Post;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class PostInsertRequest {

    private MemberDTO member;
    private List<String> hashtags = new ArrayList<>();


    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MemberDTO {
        private String uniqueKey;
    }
}
