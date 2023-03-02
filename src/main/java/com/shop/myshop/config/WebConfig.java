package com.shop.myshop.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {

        //모든 경로
        long MAX_AGE_SECS = 3600;
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:3000", "http://ec2-13-124-193-194.ap-northeast-2.compute.amazonaws.com:3000")
                .allowedMethods("GET", "POST", "PATCH", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(true)
                .maxAge(MAX_AGE_SECS);
    }
}
