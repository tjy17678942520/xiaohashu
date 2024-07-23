package com.quanxiaoha.xiaohashu.auth.service.impl;

import cn.hutool.core.util.RandomUtil;
import com.quanxiaoha.framework.common.exception.BizException;
import com.quanxiaoha.framework.common.response.Response;
import com.quanxiaoha.xiaohashu.auth.constant.RedisKeyConstants;
import com.quanxiaoha.xiaohashu.auth.enums.ResponseCodeEnum;
import com.quanxiaoha.xiaohashu.auth.model.vo.verificationcode.SendVerificationCodeReqVO;
import com.quanxiaoha.xiaohashu.auth.service.VerificationCodeService;
import com.quanxiaoha.xiaohashu.auth.sms.AliyunSmsHelper;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class VerificationCodeServiceImpl implements VerificationCodeService {

    @Resource
    private RedisTemplate<String,Object> redisTemplate;

    @Resource(name = "taskExecutor")
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;

    @Resource
    private AliyunSmsHelper aliyunSmsHelper;

    @Override
    public Response<?> send(SendVerificationCodeReqVO sendVerificationCodeReqVO) {
        // 手机号
        String phone = sendVerificationCodeReqVO.getPhone();

        // 构建验证码 Redis key
        String key = RedisKeyConstants.buildVerificationCodeKey(phone);

        //判断是否发送了验证码
        boolean isSend = redisTemplate.hasKey(key);

        if (isSend){
            //若之前的验证码未过期，则提示发送频繁
            throw new BizException(ResponseCodeEnum.VERIFICATION_CODE_SEND_FREQUENTLY);
        }
        // 生成6位随机数字验证码
        String verificationCode  = RandomUtil.randomNumbers(6);

        // TODO: 2024/7/21 调用第三方信息
        threadPoolTaskExecutor.submit(()->{
            String signName = "个人商城注册登录验证码";
            String templateCode = "SMS_464831050";
            String templateParam =  String.format("{\"code\":\"%s\"}", verificationCode);
            aliyunSmsHelper.sendMessage(signName, templateCode, phone, templateParam);
        });
        log.info("==>手机号：{}，已发送验证码：[{}]",phone,verificationCode);
        // 存储验证码到 redis ，并设置过期时间为三分钟
        redisTemplate.opsForValue().set(key,verificationCode,3, TimeUnit.MINUTES);
        return Response.success();
    }
}
