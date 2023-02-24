package com.shop.myshop.service;

import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.shop.myshop.domain.*;
import com.shop.myshop.dto.PostResponse;
import com.shop.myshop.repository.*;
import com.shop.myshop.utils.PageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final MemberRepository memberRepository;
    private final HashtagRepository hashTagRepository;
    private final PostHashtagMapRepository postHashtagMapRepository;
    private final PostLikeDislikeRepository postLikeDislikeRepository;
    private final ImagesRepository imagesRepository;
    private final JPAQueryFactory queryFactory;

    @Override
    @Transactional
    public PostLikeDislike likeDislikePost(Long postId, String memberUniqueKey, String type) {
        Member member = memberRepository.findByUniqueKey(memberUniqueKey).orElseThrow(() -> new IllegalArgumentException("NOT FOUND"));
        Post post = postRepository.findById(postId).orElseThrow(() -> new IllegalArgumentException("NOT FOUND"));

        LikeDislikeType likeDislikeType = LikeDislikeType.valueOf(type.toUpperCase());
        PostLikeDislike postLikeDislike = PostLikeDislike.createPostLikeDislike(post, member, likeDislikeType);

        return postLikeDislikeRepository.save(postLikeDislike);
    }

    @Override
    public PageResponse<PostResponse> getPosts(int page, int size) {
        QPost post = QPost.post;
        QImages images = QImages.images;
        QPostLikeDislike postLikeDislike = QPostLikeDislike.postLikeDislike;

        JPAQuery<Post> query = queryFactory.selectDistinct(post)
                .from(post)
                .leftJoin(post.images, images).fetchJoin()
                .leftJoin(post.postLikeDislikes, postLikeDislike).fetchJoin()
                .orderBy(post.createdDate.desc());

        query.where(post.hide.eq(false));

        long totalCount = query.fetchCount();

        List<Post> posts = query
                .offset((long) page * size)
                .limit(size)
                .fetch();

        List<PostResponse> content = posts.stream()
                .map(PostResponse::new)
                .collect(Collectors.toList());

        int totalPage = (int) Math.ceil((double) totalCount / size);
        boolean isLast = (long) (page + 1) * size >= totalCount;

        return new PageResponse<>(content, page, size, totalCount, totalPage, isLast);
    }

    @Override
    @Transactional
    public Post createPost(String memberUniqueKey, String content, List<String> hashtags, List<String> images, boolean isHide) {
        Member member = memberRepository.findByUniqueKey(memberUniqueKey).orElseThrow(() -> new IllegalArgumentException("NOT FOUND"));
        Post savedPost = postRepository.save(Post.createPost(member, content, isHide));

        hashtags.forEach((v) -> {
            if (!validateDuplicateTag(v)) {
                Hashtag newTag = hashTagRepository.save(Hashtag.createHashtag(v));
                postHashtagMapRepository.save(PostHashtagMap.createPostHashtag(savedPost, newTag));
            } else {
                Hashtag existedHashtag = hashTagRepository.findByName(v).get();
                postHashtagMapRepository.save(PostHashtagMap.createPostHashtag(savedPost, existedHashtag));
            }
        });

        images.forEach(v -> {
            imagesRepository.save(Images.createImage(savedPost, v));
        });

        return savedPost;
    }

    private boolean validateDuplicateTag(String name) {
        Optional<Hashtag> hashtag = hashTagRepository.findByName(name);
        return hashtag.isPresent();
    }
}
