package com.shop.myshop.service;

import com.shop.myshop.domain.Member;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

public interface MemberService {

    List<Member> findMembers();

    Member findOne(Long id);

    Long join(Member member);

    void update(Long id, String name);

    Member getByCredentials(String email, String password, PasswordEncoder encoder);
}
