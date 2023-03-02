package com.shop.myshop.utils;

import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class CookieHelper {

    private static final String COOKIE_PATH = "/";
    private static final boolean USE_HTTPS = false;
    private static final boolean READONLY = false;
//    private static final String DOMAIN = "ec2-13-124-193-194.ap-northeast-2.compute.amazonaws.com";
    private static final String DOMAIN = "localhost";

    public static void extendExpirationTime(HttpServletRequest request, HttpServletResponse response) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                insert(response, cookie.getName(), cookie.getValue());
            }
        }
    }

    //도메인 넘기는 쿠키 https://annajin.tistory.com/211
    public static void insert(HttpServletResponse response, String key, String value) {
        int MAX_AGE = 604800;
        ResponseCookie cookie = ResponseCookie.from(key, value)
                .domain(DOMAIN)
                .path(COOKIE_PATH)
                .secure(READONLY)
                .httpOnly(USE_HTTPS)
                .maxAge(MAX_AGE)
                .build();
        response.addHeader("Set-Cookie", cookie.toString());
    }
}
