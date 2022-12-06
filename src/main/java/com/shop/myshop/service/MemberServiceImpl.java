package com.shop.myshop.service;

import com.shop.myshop.domain.Member;
import com.shop.myshop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;

    @Transactional
    public Long join(Member member) {
        try {
            validateDuplicateMember(member); // 중복 회원 검증
            memberRepository.save(member);
            return member.getId();
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public void update(Long id, String name) {
        Member member = memberRepository.findById(id).get();
        member.setName(name);
    }

    private void validateDuplicateMember(Member member) {
        //Exception
        // 혹시나 모를 상황을 대비해 DB에 유니크 조건을 걸어주는게 좋다
        Optional<Member> findMembers = memberRepository.findByEmail(member.getEmail());
        if (findMembers.isPresent()) {
            throw new IllegalStateException("중복 이메일");
        }
    }

    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    public Member getByCredentials(String email, String password, final PasswordEncoder encoder) {
        Optional<Member> member = memberRepository.findByEmail(email);

        if (member.isPresent() && encoder.matches(password, member.get().getPassword())) {
            return member.get();
        }

        return null;
    }

    public Member findOne(Long id) {
        return memberRepository.findById(id).get();
    }
}
