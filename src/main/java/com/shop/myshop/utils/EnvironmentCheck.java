package com.shop.myshop.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Slf4j
@Component
public class EnvironmentCheck {

    private final Environment env;

    public EnvironmentCheck(Environment ev) {
        this.env = ev;
    }

    @PostConstruct
    public void init() {
        String url = env.getProperty("url");
        String username = env.getProperty("username");
        String password = env.getProperty("password");

        log.info("env url={}", url);
        log.info("env username={}", username);
        log.info("env password={}", password);
    }
}
