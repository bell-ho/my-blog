package com.shop.myshop.repository;

import com.shop.myshop.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    List<Member> findByName(String name);

    Optional<Member> findByEmail(String email);

    Boolean existsByEmail(String email);

    Optional<Member> findByEmailAndPassword(String email, String password);

    @Query("select m from Member m where m.email = :email and m.password = :password")
    Optional<Member> findMembers(@Param("email") String email, @Param("password") String password);

}
