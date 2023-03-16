package com.shop.myshop.service;

import com.shop.myshop.domain.Member;

public interface MemberService {
    Member findByUniqueKey(String key);
    Member join(Member member);
}
