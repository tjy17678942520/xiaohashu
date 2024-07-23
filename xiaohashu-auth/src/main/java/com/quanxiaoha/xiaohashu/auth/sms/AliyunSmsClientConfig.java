package com.quanxiaoha.xiaohashu.auth.sms;

import com.aliyun.dysmsapi20170525.Client;
import com.aliyun.teaopenapi.models.Config;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author TuJiayuan
 * @Date 2024/7/23 13:49
 * @Version 1.0
 * @Description [用于初始化一个短信发送客户端 注入Spring容器中]
 */
@Slf4j
@Configuration
public class AliyunSmsClientConfig {

    @Resource
    private AliyunAccessKeyProperties aliyunAccessKeyProperties;

    /**
     * @Description 初始化短信发送客户端
     * @Param
     * @Return
     */
    @Bean
    public Client smsClient() {
        try {
            Config config = new Config()
                    .setAccessKeyId(aliyunAccessKeyProperties.getAccessKeyId())
                    .setAccessKeySecret(aliyunAccessKeyProperties.getAccessKeySecret());
            config.endpoint = "dysmsapi.aliyuncs.com";

            return new Client(config);
        }catch (Exception e){
            log.error("初始化短信发送客户端失败",e);
            return null;
        }
    }
}
