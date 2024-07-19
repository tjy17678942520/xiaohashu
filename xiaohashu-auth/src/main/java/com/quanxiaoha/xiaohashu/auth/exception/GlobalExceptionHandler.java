package com.quanxiaoha.xiaohashu.auth.exception;

import com.quanxiaoha.framework.common.exception.BizException;
import com.quanxiaoha.framework.common.response.Response;
import com.quanxiaoha.xiaohashu.auth.enums.ResponseCodeEnum;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Optional;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 获取自定义业务异常
     *
     */
    @ExceptionHandler({BizException.class})
    public Response<Object> handleBizException(HttpServletRequest request, BizException e) {
        log.warn("{} request fail,errorCode {},errorMessage:{}", request.getRequestURI(), e.getErrorCode(),e.getErrorMessage());
        return Response.fail(e.getErrorCode(),e.getErrorMessage());
    }

    /**
     * 捕获参数校验异常
     *
     * @return
     */
    @ExceptionHandler({MethodArgumentNotValidException.class})
    @ResponseBody
    public Response<Object> handleMethodArgumentNotValidException(HttpServletRequest request, MethodArgumentNotValidException e) {
        //参数异常错误码
        String errorCode = ResponseCodeEnum.PRODUCT_NOT_FOUND.getErrorCode();

        //获取 BindingResult
        BindingResult bindingResult = e.getBindingResult();

        StringBuilder stringBuilder = new StringBuilder();

        // 获取校验不通过的字段，并组合错误信息，格式为： email 邮箱格式不正确, 当前值: '123124qq.com';
        Optional.ofNullable(bindingResult.getFieldErrors()).ifPresent(errors ->{
            errors.forEach(error ->{
                stringBuilder.append(error.getField()).append(" ").append(error.getDefaultMessage()).append(",当前值‘").append(error.getRejectedValue()).append("';");
            });
        });

        //错误信息
      String errorMeassage =  stringBuilder.toString();

      log.warn("{} requst error,errorCode:{},errorMessage:{}",request.getRequestURI(),errorCode,errorMeassage);

      return Response.fail(errorCode,errorMeassage);
    }

    /**
     * 处理其他异常
     * @param request
     * @param e
     * @return
     */
    @ExceptionHandler({Exception.class})
    @ResponseBody
    public Response<Object> handleOtherException(HttpServletRequest request, Exception e) {
        log.error("{} request error,",request.getRequestURI(),e);
        return Response.fail(ResponseCodeEnum.SYSTEM_ERROR);
    }
}
