package com.quanxiaoha.framework.common.exception;

public interface BaseExceptionInterface {

    /**
     * 获取异常编码
     * @return
     */
     String getErrorCode();

    /**
     * 获取异常信息
     */
     String getErrorMessage();


}
