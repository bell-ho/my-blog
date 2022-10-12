package com.shop.myshop.service;

import com.shop.myshop.domain.Member;

import java.util.List;

public interface MemberService {

    List<Member> findMembers();

    Member findOne(Long id);

    Long join(Member member);
}
