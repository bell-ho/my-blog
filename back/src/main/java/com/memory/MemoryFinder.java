package com.memory;

import lombok.extern.slf4j.Slf4j;

import javax.annotation.PostConstruct;

@Slf4j
public class MemoryFinder {

    public Memory get() {
        // 최대 max 값
        long max = Runtime.getRuntime().maxMemory();

        // JVM이 확보한 total 메모리
        long total = Runtime.getRuntime().totalMemory();

        // free 메모리
        long free = Runtime.getRuntime().freeMemory();
        long used = total - free;
        return new Memory(used, max);
    }

    @PostConstruct // 빈 등록
    public void init() {
        log.info("init memoryFinder");
    }
}
