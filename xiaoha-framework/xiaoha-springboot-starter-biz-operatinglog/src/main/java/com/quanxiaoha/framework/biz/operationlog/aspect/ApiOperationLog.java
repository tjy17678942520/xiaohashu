package com.quanxiaoha.framework.biz.operationlog.aspect;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)//这个元注解用于指定注解的保留策略，即注解在何时生效
@Target(ElementType.METHOD)
@Documented
public @interface ApiOperationLog {
    /**
     * API 功能描述
     */
    String  description() default "";
}
