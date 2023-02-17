package com.shop.myshop.service;

import com.shop.myshop.domain.Member;
import com.shop.myshop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;

    @Override
    public Member findByUniqueKey(String key) {
        return memberRepository.findByUniqueKey(key);
    }

    @Override
    @Transactional
    public Member join(Member member) {
        try {
//            validateDuplicateMember(member); // 중복 회원 검증
            memberRepository.save(member);
            return member;
        } catch (Exception e) {
            throw e;
        }
    }
}
