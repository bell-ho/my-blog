package com.shop.myshop.security;

import com.shop.myshop.domain.Member;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Component
public class TokenProvider {

    private static final String SECRET_KEY = "NSDGjsdfSDG35f3";

    //  토큰 생성
    public String create(Member member) {
        Date expiryDate = Date.from(Instant.now().plus(1, ChronoUnit.DAYS));

        // 토큰 생성
        return Jwts.builder()
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                .setSubject(member.getNickName())
                .setIssuer("jh")
                .setIssuedAt(new Date())
                .setExpiration(expiryDate).compact();
    }

    // 토큰 디코딩 및 파싱 위조여부 확인
    public String validateAndGetUserId(String token) {

        Claims claims = Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();

        if (claims.getExpiration().before(new Date())) {
            throw new IllegalStateException("유효기간 지남");
        }

        return claims.getSubject();
    }
}
