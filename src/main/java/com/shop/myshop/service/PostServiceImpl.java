package com.shop.myshop.service;

import com.shop.myshop.domain.*;
import com.shop.myshop.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

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
    public List<Post> getPosts() {
        return postRepository.findAll(Sort.by(Sort.Direction.DESC, "createdDate"));
    }

    @Override
    @Transactional
    public Post createPost(String memberUniqueKey, String content, List<String> hashtags, List<String> images) {
        Member member = memberRepository.findByUniqueKey(memberUniqueKey).orElseThrow(() -> new IllegalArgumentException("NOT FOUND"));
        Post savedPost = postRepository.save(Post.createPost(member, content));

        hashtags.forEach((v) -> {
            if (!validateDuplicateTag(v)) {
                Hashtag newTag = hashTagRepository.save(Hashtag.createHashtag(v));
                postHashtagMapRepository.save(PostHashtagMap.createPostHashtag(savedPost, newTag));
            } else {
                Hashtag existedHashtag = hashTagRepository.findByName(v).get();
                postHashtagMapRepository.save(PostHashtagMap.createPostHashtag(savedPost, existedHashtag));
            }
        });

        images.forEach(v->{
            imagesRepository.save(Images.createImage(savedPost, v));
        });

        return savedPost;
    }

    private boolean validateDuplicateTag(String name) {
        Optional<Hashtag> hashtag = hashTagRepository.findByName(name);
        return hashtag.isPresent();
    }
}
