package com.quanxiaoha.xiaohashu.auth.sms;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Author TuJiayuan
 * @Date 2024/7/22 16:29
 * @Version 1.0
 * @Description [读取阿里云配置信息文件中的信息]
 */
@ConfigurationProperties(prefix = "aliyun")
@Component
@Data
public class AliyunAccessKeyProperties {
    private String accessKeyId;
    private String accessKeySecret;
}
