package com.quanxiaoha.xiaohashu.auth;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

@Slf4j
@SpringBootTest
public class RedisTests {

    @Autowired
    private RedisTemplate<String,Object> redisTemplate;


    /**
     * set key value
     */
    @Test
    void testSetKeyValue() {
        // 添加一个 key 为 name, value 值为 犬小哈
        redisTemplate.opsForValue().set("name", "犬小哈");
    }

    /**
     * 判断某一个key是否存在
     */
    @Test
    void testHasKey(){
        log.info("name key 是否存在: {}", redisTemplate.hasKey("name"));
    }

    /**
     * 获取某一个key
     */
    @Test
    void testGetKeyValue(){
        log.info("name key 对应的 value: {}", redisTemplate.opsForValue().get("name"));
    }

    /**
     * 删除某一个 key
     */
    @Test
    void testDeleteKey(){
        redisTemplate.delete("name");
    }
}
