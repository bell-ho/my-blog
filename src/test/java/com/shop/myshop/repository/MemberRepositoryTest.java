package com.shop.myshop.repository;

import com.shop.myshop.domain.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import static org.assertj.core.api.Assertions.assertThat;
import java.util.Optional;

@SpringBootTest
public class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @Test
    @Transactional //트랜잭션이 Test에 있으면 테스트 후에 디비를 롤백함
    @Rollback(value = false)
    public void MemberRepoTest() throws Exception {
        //given
        Member member = new Member();
        member.setName("MemA");

        //when
        Long id = memberRepository.save(member).getId();
        Optional<Member> byId = memberRepository.findById(id);

        //then
        assertThat(byId.get().getId()).isEqualTo(member.getId());
        assertThat(byId.get().getName()).isEqualTo(member.getName());
//        assertThat(byId).isEqualTo(member);
    }
}
