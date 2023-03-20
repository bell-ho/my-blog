package com.shop.myshop.service;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.shop.myshop.domain.*;
import com.shop.myshop.dto.PostResponse;
import com.shop.myshop.repository.*;
import com.shop.myshop.utils.PageResponse;
import lombok.RequiredArgsConstructor;
import org.hibernate.criterion.Projection;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.shop.myshop.utils.Common.keywordValidation;

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

        if (postLikeDislikeRepository.existsByPostIdAndMemberUniqueKey(postId, memberUniqueKey)) {
            return null;
        }

        Member member = memberRepository.findByUniqueKey(memberUniqueKey).orElseThrow(() -> new IllegalArgumentException("NOT FOUND"));
        Post post = postRepository.findById(postId).orElseThrow(() -> new IllegalArgumentException("NOT FOUND"));

        LikeDislikeType likeDislikeType = LikeDislikeType.valueOf(type.toUpperCase());
        PostLikeDislike postLikeDislike = PostLikeDislike.createPostLikeDislike(post, member, likeDislikeType);

        return postLikeDislikeRepository.save(postLikeDislike);
    }

    @Override
    public PageResponse<PostResponse> getPosts(int page, int size, String keyword) {
        QPost post = QPost.post;
        QImages images = QImages.images;
        QPostLikeDislike postLikeDislike = QPostLikeDislike.postLikeDislike;
        QPostHashtagMap postHashtagMap = QPostHashtagMap.postHashtagMap;
        QHashtag hashtag = QHashtag.hashtag;

        JPAQuery<Post> query = queryFactory.selectDistinct(post)
                .from(post)
                .leftJoin(post.images, images).fetchJoin()
                .leftJoin(post.postLikeDislikes, postLikeDislike).fetchJoin()
                .leftJoin(post.postHashtagMaps, postHashtagMap)
                .leftJoin(postHashtagMap.hashtag, hashtag)
                .orderBy(post.id.desc(), post.createdDate.desc());

        if (keywordValidation(keyword) != null) {
            query.where(
                    hashtag.name.eq(keyword),
                    post.hide.eq(false).or(post.hide.eq(true))
            );
        } else {
            query.where(post.hide.eq(false));
        }

        long totalCount = query.fetchCount();

//        List<PostResponse> fetch =
//                query
//                        .select(Projections.bean(PostResponse.class, post))
//                        .from(post)
//                        .fetch();

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

    @Override
    @Transactional
    public void deletePost(Long postId) {
        postRepository.deleteById(postId);
    }

    private boolean validateDuplicateTag(String name) {
        Optional<Hashtag> hashtag = hashTagRepository.findByName(name);
        return hashtag.isPresent();
    }
}
