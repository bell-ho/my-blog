package com.shop.myshop.service;

import com.shop.myshop.domain.Hashtag;
import com.shop.myshop.domain.Member;
import com.shop.myshop.domain.Post;
import com.shop.myshop.domain.PostHashtagMap;
import com.shop.myshop.repository.HashtagRepository;
import com.shop.myshop.repository.MemberRepository;
import com.shop.myshop.repository.PostHashtagMapRepository;
import com.shop.myshop.repository.PostRepository;
import lombok.RequiredArgsConstructor;
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

    @Override
    @Transactional
    public Post createPost(Long memberId, List<String> hashtags) {
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new IllegalArgumentException("NOT FOUND"));
        Post savedPost = postRepository.save(Post.createPost(member));

        hashtags.forEach((v) -> {
            if (validateDuplicateTag(v)) {
                Hashtag savedTag = hashTagRepository.save(Hashtag.createHashtag(v));
                PostHashtagMap savedPostHashtag = postHashtagMapRepository.save(PostHashtagMap.createPostHashtag(savedPost, savedTag));
            }
        });

        return savedPost;
    }

    private boolean validateDuplicateTag(String name) {
        Optional<Hashtag> hashtag = hashTagRepository.findByName(name);
        return hashtag.isPresent();
    }
}
