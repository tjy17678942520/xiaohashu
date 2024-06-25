package com.quanxiaoha.framework.biz.operationlog.aspect;

import com.quanxiaoha.framework.common.util.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;


@Aspect
@Component
@Slf4j
public class ApiOperationLogAspect {

    /** 已自定义 @ApiOperationLog 注解为切点，凡是添加 @ApiOperationLog 的方法，都会执行环绕中的代码 */
    @Pointcut("@annotation(com.quanxiaoha.framework.biz.operationlog.aspect.ApiOperationLog)")
    public void apiOperationLogPointcut() {}

    /**
     * 环绕通知
     * @param joinPoint
     * @return
     * @throws Throwable
     */
    @Around("apiOperationLogPointcut()")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
            //请求开始时间
            long startTime = System.currentTimeMillis();

            //MDC
            MDC.put("traceId", UUID.randomUUID().toString());

            //获取被请求的类和和方法
            String className = joinPoint.getTarget().getClass().getSimpleName();
            String methodName = joinPoint.getSignature().getName();

            //请求参数
            Object[] args = joinPoint.getArgs();

            //入参转JSON 字符串
            String argsJsonStr = Arrays.stream(args).map(toJsonStr()).collect(Collectors.joining(", "));

            //功能描述信息
            String description  = getApiOperationLogDescription(joinPoint);

            //打印相关参数
            log.info("====== 请求开始: [{}], 入参: {}, 请求类: {}, 请求方法: {} =================================== ",
                    description, argsJsonStr, className, methodName);
            Object result = joinPoint.proceed();
            // 执行耗时
            long executionTime = System.currentTimeMillis() - startTime;
            // 打印出参等相关信息
            log.info("====== 请求结束: [{}], 耗时: {}ms, 出参: {} =================================== ",
                    description, executionTime, JsonUtils.toJsonString(result));
        return result;
    }

    /**
     * 获取注解的描述信息
     * @param joinPoint
     * @return
     */
    private String getApiOperationLogDescription(ProceedingJoinPoint joinPoint){
        //1、从ProceedingJoinPoint 获取方法签名 MethodSignature
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();

        //2、使用MethodSignature 获取当前备注接的方法 Method
        Method method = signature.getMethod();

        //3、从Method 中提取  LogExecution 注解
        ApiOperationLog apiOperationLog  = method.getAnnotation(ApiOperationLog.class);

        //4、从LogExecution 注解中获取 description 描述信息
        return  apiOperationLog.description();

    }



    /**
     * 转 JSON 字符串
     * @return
     */
    private Function<Object, String> toJsonStr() {
        return JsonUtils::toJsonString;
    }




}
