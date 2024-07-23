package com.quanxiaoha.xiaohashu.auth.sms;

import com.aliyun.dysmsapi20170525.Client;
import com.aliyun.dysmsapi20170525.models.SendCardSmsResponse;
import com.aliyun.dysmsapi20170525.models.SendSmsRequest;
import com.aliyun.dysmsapi20170525.models.SendSmsResponse;
import com.aliyun.teautil.models.RuntimeOptions;
import com.quanxiaoha.framework.common.util.JsonUtils;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @Author TuJiayuan
 * @Date 2024/7/23 13:57
 * @Version 1.0
 * @Description [短信发送工具]
 */
@Component
@Slf4j
public class AliyunSmsHelper {
    @Resource
    private Client client;

    /**
     * @Description 发送短信
     * @Param
     * @Return
     */
    public boolean sendMessage(String signName, String templateCode, String phoneNumber, String templateParam){
        SendSmsRequest sendSmsRequest = new SendSmsRequest()
                .setSignName(signName)
                .setTemplateCode(templateCode)
                .setPhoneNumbers(phoneNumber)
                .setTemplateParam(templateParam);

        RuntimeOptions runtimeOptions = new RuntimeOptions();

       try {
           log.info("============>开始发送短信，phone：{}，signName：{}，templateCode：{}，templateParam：{}", phoneNumber, signName, templateCode, templateParam);

           SendSmsResponse response = client.sendSmsWithOptions(sendSmsRequest, runtimeOptions);
           log.info("============>发送短信成功，response：{}", JsonUtils.toJsonString(response));
           return true;
       }catch (Exception e){
           log.error("==> 短信发送错误: ", e);
           return false;
       }
    }
}
