package com.shop.myshop.repository;

import com.shop.myshop.domain.Member;
import com.shop.myshop.domain.Post;
import com.shop.myshop.domain.PostLikeDislike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostLikeDislikeRepository extends JpaRepository<PostLikeDislike, Long> {
    boolean existsByPostIdAndMemberUniqueKey(Long postId, String memberUniqueKey);
}
