package com.quanxiaoha.xiaohashu.auth;

import com.alibaba.druid.filter.config.ConfigTools;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.swing.*;

@SpringBootTest
@Slf4j
public class DruidTests {

    @SneakyThrows
    @Test
    void testEncodePassword() {
        String password = "1qaz!QAZ";
        String[] str = ConfigTools.genKeyPair(512);

        //私钥
        log.info("privateKey:{}", str[0]);

        //公钥
        log.info("publicKey:{}", str[1]);

        //通过私钥加密密码
        String encodePassword = ConfigTools.encrypt(str[0], password);
        log.info("encodePassword:{}", encodePassword);
    }
}
