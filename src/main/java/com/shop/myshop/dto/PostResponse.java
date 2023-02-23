package com.shop.myshop.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.shop.myshop.domain.Images;
import com.shop.myshop.domain.LikeDislikeType;
import com.shop.myshop.domain.Post;
import com.shop.myshop.domain.PostLikeDislike;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class PostResponse {

    private Long id;

    private String content;

    private String nickName;

    @Builder.Default
    private List<ImagesDTO> images = new ArrayList<>();

    @Builder.Default
    private List<PostLikeDislikeDTO> postLikeDislikes = new ArrayList<>();

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private Timestamp createDate;

    @Builder
    public PostResponse(Post post) {
        this.id = post.getId();
        this.content = post.getContent();
        this.nickName = post.getMember().getNickName();
        this.postLikeDislikes = post.getPostLikeDislikes().stream().map(PostLikeDislikeDTO::new).collect(Collectors.toList());
        this.images = post.getImages().stream().map(ImagesDTO::new).collect(Collectors.toList());
        this.createDate = Timestamp.valueOf(post.getCreatedDate());
    }

    @Getter
    @Setter
    public static class PostLikeDislikeDTO {
        private Long id;
        private String memberUniqueKey;
        private String type;

        @Builder
        public PostLikeDislikeDTO(PostLikeDislike entity) {
            this.id = entity.getId();
            this.memberUniqueKey = entity.getMember().getUniqueKey();
            this.type = entity.getType().name().toLowerCase();
        }
    }

    @Getter
    @Setter
    public static class ImagesDTO {
        private Long id;
        private String src;

        @Builder
        public ImagesDTO(Images images) {
            this.id = images.getId();
            this.src = images.getSrc();
        }
    }
}
