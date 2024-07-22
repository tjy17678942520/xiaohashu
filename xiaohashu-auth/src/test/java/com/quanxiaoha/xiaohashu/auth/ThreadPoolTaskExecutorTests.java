package com.quanxiaoha.xiaohashu.auth;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.io.InputStream;
import java.util.stream.IntStream;

/**
 * @Author TuJiayuan
 * @Date 2024/7/22 15:31
 * @Version 1.0
 */
@Slf4j
@SpringBootTest
public class ThreadPoolTaskExecutorTests {
    @Resource
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;

    /**
     * @Description 测试线程池
     * @Param
     * @Return
     */
    @Test
    void test() {
        IntStream.range(1,10).forEach((i)->{
                threadPoolTaskExecutor.submit(()->{
                    log.info("测试异步线程"+i);
                });
        });
    }
}
